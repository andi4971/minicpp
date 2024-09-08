package expr

import SemanticTest
import org.azauner.minicpp.ast.generator.exception.SemanticException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SimpleExprText: SemanticTest() {
    @Test
    fun testSimpleExprInvalidBoolType() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                  int test = 1;
                  bool test2 = false;
                  int testerg;
                  testerg = test + test2;
                
            """.trimIndent()
            )
        }
    }
}
