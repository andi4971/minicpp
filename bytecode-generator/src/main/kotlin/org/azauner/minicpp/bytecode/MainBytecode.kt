package org.azauner.minicpp.bytecode

import org.azauner.minicpp.ast.generator.*
import kotlin.io.path.Path
import kotlin.io.path.inputStream
import kotlin.time.Duration
import kotlin.time.measureTime

fun main() {
    evaluate("golife10000")
    evaluate("golife20000")
    evaluate("golife40000")
    /*    evaluate("shortsrc")
        evaluate("mediumsrc")
        evaluate("longsrc")
        evaluate("verylongsrc")
        evaluate("superlongsrc")*/
}

fun getMiniCppIS(fileName: String) = Path("$fileName.mcpp").inputStream()

data class EvalResult(val duration: Duration, val memory: String, val rawMemory: Long) {
    override fun toString() = "Duration: \t\t $duration, Memory:\t $memory Raw bytes\t $rawMemory"
}

fun evaluate(className: String) {
    var nodecount = generateParse(getMiniCppIS(className)).let { parseTree -> countMiniCppEntries(parseTree) }
    val astNodeCount = generateAstForATG(getMiniCppIS(className), className).let { ast -> countMiniCppEntries(ast) }
    val listener = measureTimeMultiple { generateAstForFileListener(getMiniCppIS(className), className) }
    val visitor = measureTimeMultiple { generateASTForFileVisitor(getMiniCppIS(className), className) }
    val atg = measureTimeMultiple { generateAstForATG(getMiniCppIS(className), className) }
    val parse = measureTimeMultiple { generateParse(getMiniCppIS(className)) }
    val parseNoParseTree = measureTimeMultiple { generateParse(getMiniCppIS(className), false) }


    println("class:     $className")
    println("nodes:     $nodecount")
    println("astNodes:  $astNodeCount")
    //print durations
    println("Parse:     $parse")
    println("NoTreeP:   $parseNoParseTree")
    println("Visitor:   $visitor")
    println("Listener:  $listener")
    println("ATG:       $atg")
    println()
}

//measure time 3 times for a block
fun measureTimeMultiple(block: () -> Unit): EvalResult {
    System.gc()
    var memPrev = getMem()
    val time = measureTime { block() }
    val mem = getMem() - memPrev
    System.gc()
    memPrev = getMem()

    val time2 = measureTime { block() }
    val mem2 = getMem() - memPrev

    System.gc()
    memPrev = getMem()

    val time3 = measureTime { block() }
    val mem3 = getMem() - memPrev
    System.gc()
    val dur = (time + time2 + time3) / 3
    val memAvg = (mem + mem2 + mem3) / 3
    return EvalResult(dur, bytesToHumanReadableSize(memAvg.toDouble()), memAvg)
}

fun getMem() = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()

fun bytesToHumanReadableSize(bytes: Double) = when {
    bytes >= 1 shl 30 -> "%.1f GB".format(bytes / (1 shl 30))
    bytes >= 1 shl 20 -> "%.1f MB".format(bytes / (1 shl 20))
    bytes >= 1 shl 10 -> "%.0f kB".format(bytes / (1 shl 10))
    else -> "$bytes bytes"
}
