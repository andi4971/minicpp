package org.azauner.minicpp.sourcecode

import java.nio.file.Files
import kotlin.io.path.Path

fun main() {
    val fileName = "Sieve.mcpp"
    var inputStream = ClassLoader.getSystemResourceAsStream(fileName)
    val className = fileName.substringBefore(".mcpp")


    //walker.walk(MiniCppListener(parser), parser.miniCpp())
    val result = org.azauner.minicpp.ast.generator.generateASTForFileVisitor(inputStream, className)
    inputStream = ClassLoader.getSystemResourceAsStream(fileName)
    val result2 = org.azauner.minicpp.ast.generator.generateAstForFileListener(inputStream, className)
    //println(result.prettyPrint())
    Files.writeString(Path("examples/output.mcpp"), result.generateSourceCode())
    Files.writeString(Path("examples/output_listener.mcpp"), result2.generateSourceCode())

}
