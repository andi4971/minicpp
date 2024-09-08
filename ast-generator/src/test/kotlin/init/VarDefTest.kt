package init

import SemanticTest
import org.azauner.minicpp.ast.generator.exception.SemanticException
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class VarDefTest: SemanticTest() {

    @Test
    fun testValidVarDef() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                int a = 5, b = 6, *c;
            """.trimIndent()
            )
        }
    }

    @Test
    fun testInvalidVarDef() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                int a = 5, b = true;
            """.trimIndent()
            )
        }
    }
}
