package org.azauner.minicpp.sourcecode

import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.inputStream

fun main() {
    val fileName = "BubbleSort.mcpp"
    var inputStream = Path("examples/$fileName").inputStream()
    val className = fileName.substringBefore(".mcpp")


    //walker.walk(MiniCppListener(parser), parser.miniCpp())
    val result = org.azauner.minicpp.ast.generator.generateASTForFileVisitor(inputStream, className)
    inputStream = Path("examples/$fileName").inputStream()
    val result2 = org.azauner.minicpp.ast.generator.generateAstForFileListener(inputStream, className)
    inputStream = Path("examples/$fileName").inputStream()
    val result3 = org.azauner.minicpp.ast.generator.generateAstForATG(inputStream, className)
    //println(result.prettyPrint())
    Files.writeString(Path("examples/output.mcpp"), result.generateSourceCode())
    Files.writeString(Path("examples/output_listener.mcpp"), result2.generateSourceCode())
    Files.writeString(Path("examples/output_atg.mcpp"), result3.generateSourceCode())

}
