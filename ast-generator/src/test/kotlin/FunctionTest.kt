import org.azauner.minicpp.ast.generator.exception.SemanticException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class FunctionTest: SemanticTest() {
    @Test
    fun testFunctionPointerParameters() {
        assertDoesNotThrow {
            testCode(
                """
                  int test(int* value) {
                    
                  }
                  void main() {
                   int *test2 = nullptr;
                   test(test2);
                  }
               """.trimIndent()
            )
        }
    }

    @Test
    fun testFunctionValidReturnType() {
        assertDoesNotThrow {
            testCode(
                """
                  int test() {
                    
                  }
                  void main() {
                   int test;
                   test = test();
                  }
               """.trimIndent()
            )
        }
    }

    @Test
    fun testFunctionInvalidReturnType() {
        assertThrows<SemanticException> {
            testCode(
                """
                  bool test() {
                    
                  }
                  void main() {
                   int test;
                   test = test(test2);
                  }
               """.trimIndent()
            )
        }
    }

    @Test
    fun testFunctionPointerInvalidParameter() {
        assertThrows<SemanticException> {
            testCode(
                """
                  int test(int* value) {
                    
                  }
                  void main() {
                   test(5);
                  }
               """.trimIndent()
            )
        }
    }

    @Test
    fun testFunctionDoesNotExist() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                   test(5);
                """
                    .trimIndent()
            )
        }
    }
}
