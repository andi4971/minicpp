package expr

import SemanticTest
import org.azauner.ast.generator.exception.SemanticException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class RelExprTest: SemanticTest() {
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
}
