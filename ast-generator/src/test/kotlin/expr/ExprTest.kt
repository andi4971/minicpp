package expr

import SemanticTest
import org.azauner.minicpp.ast.generator.exception.SemanticException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class ExprTest: SemanticTest() {
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
    fun testAssignToConstInvalid() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                  const int test = 5;
                  test = 6;  
            """.trimIndent()
            )
        }
    }
}
