package expr

import SemanticTest
import org.azauner.minicpp.ast.generator.exception.SemanticException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class AndExprTest: SemanticTest() {

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
}
