package src

import org.azauner.minicpp.sourcecode.generateSourceCode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class SourceCodeGenerationTest {

    @ParameterizedTest
    @MethodSource("getTestFiles")
    fun testMultipleAstGenerationsVisitor(filename: String) {
        val sourceCode = ClassLoader.getSystemResourceAsStream(filename)
        val firstParse = org.azauner.minicpp.ast.generator.generateASTForFileVisitor(sourceCode, filename)
        val sourceCodeFromParse = firstParse.generateSourceCode()
        val secondParse =
            org.azauner.minicpp.ast.generator.generateASTForFileVisitor(sourceCodeFromParse.byteInputStream(), filename)
        val sourceCodeFromSecondParse = secondParse.generateSourceCode()
        assert(sourceCodeFromParse == sourceCodeFromSecondParse)
    }

    @ParameterizedTest
    @MethodSource("getTestFiles")
    fun testMultipleAstGenerationsListener(filename: String) {
        val sourceCode = ClassLoader.getSystemResourceAsStream(filename)
        val firstParse = org.azauner.minicpp.ast.generator.generateAstForFileListener(sourceCode, filename)
        val sourceCodeFromParse = firstParse.generateSourceCode()
        val secondParse =
            org.azauner.minicpp.ast.generator.generateAstForFileListener(
                sourceCodeFromParse.byteInputStream(),
                filename
            )
        val sourceCodeFromSecondParse = secondParse.generateSourceCode()
        assert(sourceCodeFromParse == sourceCodeFromSecondParse)
    }

    @ParameterizedTest
    @MethodSource("getTestFiles")
    fun testMultipleAstGenerationsAtg(filename: String) {
        val sourceCode = ClassLoader.getSystemResourceAsStream(filename)
        val firstParse = org.azauner.minicpp.ast.generator.generateAstForATG(sourceCode, filename)
        val sourceCodeFromParse = firstParse.generateSourceCode()
        val secondParse =
            org.azauner.minicpp.ast.generator.generateAstForATG(sourceCodeFromParse.byteInputStream(), filename)
        val sourceCodeFromSecondParse = secondParse.generateSourceCode()
        assert(sourceCodeFromParse == sourceCodeFromSecondParse)
    }


    @ParameterizedTest
    @MethodSource("getTestFiles")
    fun testMultipleAstGenerationsDifferentMethods(filename: String) {
        val sourceCode = ClassLoader.getSystemResourceAsStream(filename)
        val firstParse = org.azauner.minicpp.ast.generator.generateASTForFileVisitor(sourceCode, filename)
        val sourceCodeFromParse = firstParse.generateSourceCode()
        val secondParse =
            org.azauner.minicpp.ast.generator.generateAstForFileListener(
                sourceCodeFromParse.byteInputStream(),
                filename
            )
        val sourceCodeFromSecondParse = secondParse.generateSourceCode()
        assert(sourceCodeFromParse == sourceCodeFromSecondParse)
    }

    companion object {
        @JvmStatic
        fun getTestFiles(): List<String> {
            return listOf("Sieve.mcpp", "BubbleSort.mcpp", "GoLife.mcpp")
        }
    }


    //parameterizet test with 3 files

}
