package src

import org.azauner.minicpp.sourcecode.generateSourceCode
import kotlin.test.Test

class SourceCodeGenerationTest {

    @Test
    fun testMultipleAstGenerations() {
        val filename = "Sieve.mcpp"
        val sourceCode = ClassLoader.getSystemResourceAsStream(filename)
        val firstParse = org.azauner.minicpp.ast.generator.generateASTForFile(sourceCode, filename)
        val sourceCodeFromParse = firstParse.generateSourceCode()
        val secondParse =
            org.azauner.minicpp.ast.generator.generateASTForFile(sourceCodeFromParse.byteInputStream(), filename)
        val sourceCodeFromSecondParse = secondParse.generateSourceCode()
        assert(sourceCodeFromParse == sourceCodeFromSecondParse)
    }
}
