import org.antlr.v4.runtime.BufferedTokenStream
import org.antlr.v4.runtime.CharStreams
import org.azauner.ast.generator.aspect.SemanticErrorAspect
import org.azauner.ast.generator.exception.SemanticException
import org.azauner.ast.generator.visitor.MiniCppVisitor
import org.azauner.parser.minicppLexer
import org.azauner.parser.minicppParser
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows


class SemanticTest {

    @Test
    fun testExprInvalidAssignType() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                  bool test = true;
                  int intAssign;
                  intAssign = test;  
            """.trimIndent()
            )
        }
    }

    @Test
    fun testExprValidAssignType() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                  int test = 5;
                  int intAssign;
                  intAssign = test;  
            """.trimIndent()
            )
        }
    }

    @Test
    fun testOrExprValidAssign() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                  bool test = true;
                  bool test2 = false;
                  bool test3 = false;
                  bool testerg;
                  testerg = test && test2 && test3;
            """.trimIndent()
            )
        }
    }

    @Test
    fun testOrExprInvalidAssign() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                  bool test = true;
                  bool test2 = false;
                  int test3 = 5;
                  bool testerg;
                  testerg = test && test2 && test3;
            """.trimIndent()
            )
        }
    }

    @Test
    fun testAndExprInvalidAssign() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                  bool test = true;
                  bool test2 = false;
                  int test3 = 5;
                  bool testerg;
                  testerg = test || test2 || test3;
            """.trimIndent()
            )
        }
    }

    @Test
    fun testAndExprValidAssign() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                  bool test = true;
                  bool test2 = false;
                  bool test3 = false;
                  bool testerg;
                  testerg = test || test2 || test3;
            """.trimIndent()
            )
        }
    }


    @Test
    fun testAndExprInvalidType() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                  bool test = true;
                  int intTest = 6;
                  bool testerg;
                  testerg = test && intTest;
                
            """.trimIndent()
            )
        }
    }

    @Test
    fun testRelExprInvalidType() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                  bool test = true;
                  int test2 = 5;
                  bool testerg;
                  testerg = test == intTest;
                
            """.trimIndent()
            )
        }
    }

    @Test
    fun testRelExprInvalidBoolType() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                  bool test = true;
                  bool test2 = false;
                  bool testerg;
                  testerg = test <= intTest;
                
            """.trimIndent()
            )
        }
    }

    @Test
    fun testRelExprValidBoolType() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                  bool test = true;
                  bool test2 = false;
                  bool testerg;
                  testerg = test == test2;
                
            """.trimIndent()
            )
        }
    }

    @Test
    fun testRelExprValidIntType() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                  int test = 1;
                  int test2 = 2;
                  test2 += test;
            """.trimIndent()
            )
        }
    }


    @Test
    fun testSimpleExprInvalidBoolType() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                  int test = 1;
                  bool test2 = false;
                  int testerg;
                  testerg = test + test2;
                
            """.trimIndent()
            )
        }
    }

    @Test
    fun testSimpleExprValidIntType() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                  int test = 1;
                  int test2;
                  test2= -test;
            """.trimIndent()
            )
        }
    }

    @Test
    fun testMethodOverload() {
        assertDoesNotThrow {
            testCode(
                """
                  void test(int value) {
                  }
                  void test(bool value) {
                  }
                  void main() {
                    test(5);
                    test(true);
                  }
               """.trimIndent()
            )
        }
    }

    @Test
    fun testMethodValidReturnType() {
        assertDoesNotThrow {
            testCode(
                """
                  int test(int value) {
                    return value;
                  }
                  bool test(bool value) {
                    return value;
                  }
                  void main() {
                    int intRes;
                    bool boolRes;
                    intRes = test(5);
                    boolRes = test(true);
                  }
               """.trimIndent()
            )
        }
    }

    @Test
    fun testMethodInvalidReturnType() {
        assertThrows<SemanticException> {
            testCode(
                """
                  int test(int value) {
                    return value;
                  }
                  void main() {
                    bool boolRes;
                    boolRes = test(5);
                  }
               """.trimIndent()
            )
        }
    }

    @Test
    fun testArrayInvalidIndexType() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                    bool *arr = nullptr;
                    arr = new bool[5];
                    arr[true] = false; 
               """.trimIndent()
            )
        }
    }
    @Test
    fun testArrayValidIndexType() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                    bool *arr = nullptr;
                    arr = new bool[5];
                    arr[3] = false;
               """.trimIndent()
            )
        }
    }

    @Test
    fun testIntIncrease() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                    int test = 5;
                    test++;
                    test--;
                    --test;
                    ++test;
               """.trimIndent()
            )
        }
    }
    @Test
    fun testIntIncreaseInvalidType() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                    bool test = true;
                    test++;
                    test--;
                    --test;
                    ++test;
               """.trimIndent()
            )
        }
    }

    @Test
    fun varNotFound() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                  int test = 5;
                  int test2;
                  test2 = test + test3;
                
            """.trimIndent()
            )
        }
    }




    private fun wrapInMain(code: String) = """
        void main() {
            $code
        }
    """.trimIndent()

    private fun testCodeInMain(code: String) {
        testCode(wrapInMain(code))
    }

    private fun testCode(code: String) {
        val charStream = CharStreams.fromString(code)
        val lexer = minicppLexer(charStream)
        val tokenStream = BufferedTokenStream(lexer)
        val parser = minicppParser(tokenStream)

        MiniCppVisitor().visit(parser.miniCpp())
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            SemanticErrorAspect.doExit = false
        }
    }
}
