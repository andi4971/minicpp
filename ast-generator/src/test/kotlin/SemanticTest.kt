import org.antlr.v4.runtime.BufferedTokenStream
import org.antlr.v4.runtime.CharStreams
import org.azauner.ast.generator.aspect.SemanticErrorAspect
import org.azauner.ast.generator.visitor.MiniCppVisitor
import org.azauner.parser.minicppLexer
import org.azauner.parser.minicppParser
import org.junit.jupiter.api.BeforeAll


abstract class SemanticTest {

    private fun wrapInMain(code: String) = """
        void main() {
            $code
        }
    """.trimIndent()

    protected fun testCodeInMain(code: String) {
        testCode(wrapInMain(code))
    }

    protected fun testCode(code: String) {
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
            SemanticErrorAspect.doExit = false
        }
    }
}
