package stat

import SemanticTest
import org.azauner.minicpp.ast.generator.exception.SemanticException
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class WhileStatTest: SemanticTest() {

    @Test
    fun testValidWhileStat() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                int a = 0;
                while (a < 5) {
                  a = a + 1;
                }
            """.trimIndent()
            )
        }
    }

    @Test
    fun testInvalidWhileStat() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                int a = 0;
                while (a) {
                  a = a + 1;
                }
            """.trimIndent()
            )
        }
    }
}
