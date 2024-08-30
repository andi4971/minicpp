package stat

import SemanticTest
import org.azauner.ast.generator.exception.SemanticException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class IfStatTest: SemanticTest() {

    @Test
    fun invalidConditionExpr() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                    if (5) {
                      int a = 5;
                    }
                """.trimIndent()
            )
        }
    }

    @Test
    fun validConditionExpr() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                    if (true) {
                      int a = 5;
                    }
                """.trimIndent()
            )
        }
    }
}
