package stat

import SemanticTest
import org.azauner.ast.generator.exception.SemanticException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class InputStatTest: SemanticTest() {

    @Test
    fun testValidInputStat() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                int a;
                cin >> a;
            """.trimIndent()
            )
        }
    }

    @Test
    fun testInvalidInputStat() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                int* a;
                cin >> a;
            """.trimIndent()
            )
        }
    }
}
