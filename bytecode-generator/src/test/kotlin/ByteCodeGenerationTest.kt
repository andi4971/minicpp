import org.azauner.minicpp.bytecode.MiniCppGenerator
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ByteCodeGenerationTest {

    @ParameterizedTest
    @MethodSource("getTestFiles")
    fun testCompareAstGenerationsVisitorListener(filename: String) {
        val sourceCode = ClassLoader.getSystemResourceAsStream(filename)
        val firstParse = org.azauner.minicpp.ast.generator.generateASTForFileVisitor(sourceCode, filename)
        val byteCodeFromParse = MiniCppGenerator(firstParse).generateByteCode()
        val secondParse =
            org.azauner.minicpp.ast.generator.generateAstForFileListener(
                ClassLoader.getSystemResourceAsStream(filename),
                filename
            )
        val byteCodeFromSecondParse = MiniCppGenerator(secondParse).generateByteCode()
        assert(byteCodeFromParse.contentEquals(byteCodeFromSecondParse))
    }

    @ParameterizedTest
    @MethodSource("getTestFiles")
    fun testCompareAstGenerationsListenerATG(filename: String) {
        val sourceCode = ClassLoader.getSystemResourceAsStream(filename)
        val firstParse = org.azauner.minicpp.ast.generator.generateAstForFileListener(sourceCode, filename)
        val byteCodeFromParse = MiniCppGenerator(firstParse).generateByteCode()
        val secondParse =
            org.azauner.minicpp.ast.generator.generateAstForATG(
                ClassLoader.getSystemResourceAsStream(filename),
                filename
            )
        val byteCodeFromSecondParse = MiniCppGenerator(secondParse).generateByteCode()
        assert(byteCodeFromParse.contentEquals(byteCodeFromSecondParse))
    }

    @ParameterizedTest
    @MethodSource("getTestFiles")
    fun testCompareAstGenerationsAtgVisitor(filename: String) {
        val sourceCode = ClassLoader.getSystemResourceAsStream(filename)
        val firstParse = org.azauner.minicpp.ast.generator.generateAstForATG(sourceCode, filename)
        val byteCodeFromParse = MiniCppGenerator(firstParse).generateByteCode()
        val secondParse =
            org.azauner.minicpp.ast.generator.generateASTForFileVisitor(
                ClassLoader.getSystemResourceAsStream(filename),
                filename
            )
        val byteCodeFromSecondParse = MiniCppGenerator(secondParse).generateByteCode()
        assert(byteCodeFromParse.contentEquals(byteCodeFromSecondParse))
    }

    companion object {
        @JvmStatic
        fun getTestFiles(): List<String> {
            return listOf("Sieve.mcpp", "BubbleSort.mcpp", "GoLife.mcpp", "longsrc.mcpp")
        }
    }


    //parameterizet test with 3 files

}
