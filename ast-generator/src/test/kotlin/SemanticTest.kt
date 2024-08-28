import org.antlr.v4.runtime.BufferedTokenStream
import org.antlr.v4.runtime.CharStreams
import org.azauner.ast.generator.aspect.SemanticErrorAspect
import org.azauner.ast.generator.exception.SemanticException
import org.azauner.ast.generator.visitor.MiniCppVisitor
import org.azauner.parser.minicppLexer
import org.azauner.parser.minicppParser
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class SemanticTest {

    @Test
    fun testRelExprInvalidType() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                  bool test = true;
                  int intTest = 6;
                  bool testerg;
                  testerg = test || intTest;
                
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

    @Test
    fun varNotFound() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                  int test = 5;
                  int test2;
                  test2 =  = test + test3;
                
            """.trimIndent()
            )
        }
    }


    private fun wrapInMain(code: String) = """
        void main() {
            $code
        }
    """.trimIndent()

    private fun testCodeInMain(code: String) {
        testCode(wrapInMain(code))
    }

    private fun testCode(code: String) {
        val charStream = CharStreams.fromString(code)
        val lexer = minicppLexer(charStream)
        val tokenStream = BufferedTokenStream(lexer)
        val parser = minicppParser(tokenStream)

        MiniCppVisitor().visit(parser.miniCpp())
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            SemanticErrorAspect.isEnabled = false
        }
    }
}
