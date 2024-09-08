import org.azauner.minicpp.ast.generator.exception.SemanticException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class MethodTest: SemanticTest() {

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
    fun testMethodDuplicateDefinition() {
        assertThrows<SemanticException> {
            testCode(
                """
                  void test(int value) {
                  
                  }
                  
                  void test(int value) {
                  }
               """.trimIndent()
            )
        }
    }

    @Test
    fun testMethodDuplicateDeclaration() {
        assertThrows<SemanticException> {
            testCode(
                """
                  void test(int value); 
                  void test(int value);
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
}
