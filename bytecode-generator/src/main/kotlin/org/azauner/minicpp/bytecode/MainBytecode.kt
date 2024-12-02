package org.azauner.minicpp.bytecode

import org.azauner.minicpp.ast.generator.generateASTForFileVisitor
import org.azauner.minicpp.ast.generator.generateAstForATG
import kotlin.io.path.Path
import kotlin.io.path.inputStream
import kotlin.time.Duration
import kotlin.time.measureTime

fun main() {
    /*    val fileName = "Sieve.mcpp"
        //val fileName = "GoLife.mcpp"
        //val fileName = "BubbleSort.mcpp"
        var inputStream = Path("examples/$fileName").inputStream()
        val className = fileName.substringBefore(".mcpp")


        //walker.walk(MiniCppListener(parser), parser.miniCpp())
        val result2 = org.azauner.minicpp.ast.generator.generateAstForFileListener(inputStream, className)
        inputStream = Path("examples/$fileName").inputStream()
        val result = org.azauner.minicpp.ast.generator.generateASTForFileVisitor(inputStream, className)
        val bytes = MiniCppGenerator(result).generateByteCode()
        val bytes2 = MiniCppGenerator(result2).generateByteCode()
        Path("examples/${result.className}_list.class").toFile().writeBytes(bytes2)
        Path("examples/${result.className}.class").toFile().writeBytes(bytes)*/
    evaluate("shortsrc")
    evaluate("mediumsrc")
    evaluate("longsrc")
    evaluate("verylongsrc")
    evaluate("superlongsrc")
}

fun getMiniCppIS(fileName: String) = Path("examples/$fileName.mcpp").inputStream()

fun evaluate(className: String) {


    val visitor = measureTimeMultiple { generateASTForFileVisitor(getMiniCppIS(className), className) }
    //val listener = measureTimeMultiple { generateAstForFileListener(getMiniCppIS(className), className) }
    val atg = measureTimeMultiple { generateAstForATG(getMiniCppIS(className), className) }

    println("class:     $className")
    //print durations
    println("Visitor:   $visitor")
    //println("Listener:  $listener")
    println("ATG:       $atg")
    println()
}

//measure time 3 times for a block
fun measureTimeMultiple(block: () -> Unit): Duration {
    val time = measureTime { block() }
    val time2 = measureTime { block() }
    val time3 = measureTime { block() }
    return (time + time2 + time3) / 3
}
