package stat

import SemanticTest
import org.azauner.ast.generator.exception.SemanticException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class DeleteStatTest : SemanticTest() {

    @Test
    fun testValidDeleteStat() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                int *a = nullptr;
                a = new int[5];
                delete[] a;
            """.trimIndent()
            )
        }
    }

    @Test
    fun testInvalidDeleteStat() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                int a = 5;
                delete[] a;
            """.trimIndent()
            )
        }
    }
}
