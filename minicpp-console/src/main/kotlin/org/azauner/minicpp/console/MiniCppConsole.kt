package org.azauner.minicpp.console

import org.azauner.minicpp.sourcecode.generateSourceCode
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.inputStream

fun main(args: Array<String>) {

    val parameters = args.toMutableList()

    var useVisitor = true
    var useListener = false
    var useATG = false
    var outputSourceCode = false

    var fileName: String? = null

    if (parameters.isEmpty()) {
        println("Error: No parameters given")
        printHelp()
        return
    }

    while (parameters.isNotEmpty()) {
        val parameter = parameters.removeFirst()

        when (parameter) {
            "-h", "-help" -> {
                printHelp()
                return
            }

            "-v", "-visitor" -> {
                useVisitor = true
                useATG = false
                useListener = false
            }

            "-l", "-listener" -> {
                useVisitor = false
                useATG = false
                useListener = true
            }

            "-a", "-atg" -> {
                useVisitor = false
                useATG = true
                useListener = false
            }

            "-s", "-source" -> outputSourceCode = true
            else -> {
                if (fileName != null) {
                    println("Error: Multiple file names given")
                    return
                }
                fileName = parameter
            }
        }
    }


    if (useVisitor && useListener || useVisitor && useATG || useListener && useATG) {
        println("Error: Multiple AST generation methods given")
        return
    }


    val inputStream = fileName!!.let { Path.of(it).inputStream() }
    val className = fileName.substringBefore(".mcpp")
    val ast = when {
        useVisitor -> org.azauner.minicpp.ast.generator.generateASTForFileVisitor(inputStream, className)
        useListener -> org.azauner.minicpp.ast.generator.generateAstForFileListener(inputStream, className)
        useATG -> org.azauner.minicpp.ast.generator.generateAstForATG(inputStream, className)
        else -> {
            println("Error: No AST generation method given")
            return
        }
    }


    val bytes = org.azauner.minicpp.bytecode.MiniCppGenerator(ast).generateByteCode()
    Files.write(Path.of("$className.class"), bytes)

    if (outputSourceCode) {
        val sourceCode = ast.generateSourceCode()
        Files.writeString(Path.of("${className}_generated.mcpp"), sourceCode)
    }
}

fun printHelp() {
    println("Usage: minicpp-console [options] <file>")
    println("Options:")
    println("  -v, -visitor: Use visitor for AST generation")
    println("  -l, -listener: Use listener for AST generation")
    println("  -a, -atg: Use ATG for AST generation")
    println("  -s, -source: Output source code")
}
