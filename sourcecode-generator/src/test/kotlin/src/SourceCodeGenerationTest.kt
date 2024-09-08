package src

import generateSourceCode
import org.azauner.ast.generator.generateASTForFile
import kotlin.test.Test

class SourceCodeGenerationTest {

    @Test
    fun testMultipleAstGenerations() {
        val filename = "Sieve.mcpp"
        val sourceCode = ClassLoader.getSystemResourceAsStream(filename)
        val firstParse = generateASTForFile(sourceCode, filename)
        val sourceCodeFromParse = firstParse.generateSourceCode()
        val secondParse = generateASTForFile(sourceCodeFromParse.byteInputStream(), filename)
        val sourceCodeFromSecondParse = secondParse.generateSourceCode()
        assert(sourceCodeFromParse == sourceCodeFromSecondParse)
    }
}
