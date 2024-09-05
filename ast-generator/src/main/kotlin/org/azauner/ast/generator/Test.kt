package org.azauner.ast.generator

import java.nio.file.Files
import kotlin.io.path.Path

fun main() {
    val fileName = "Sieve.mcpp"
    val inputStream = ClassLoader.getSystemResourceAsStream(fileName)



    //walker.walk(MiniCppListener(parser), parser.miniCpp())
    val result = generateASTForFile(inputStream, fileName)
    //println(result.prettyPrint())
    Files.writeString(Path("examples/output.mcpp"), result.generateSourceCode())
}

fun Any.prettyPrint(): String {

    var indentLevel = 0
    val indentWidth = 4

    fun padding() = "".padStart(indentLevel * indentWidth)

    val toString = toString()

    val stringBuilder = StringBuilder(toString.length)

    var i = 0
    while (i < toString.length) {
        when (val char = toString[i]) {
            '(', '[', '{' -> {
                indentLevel++
                stringBuilder.appendLine(char).append(padding())
            }
            ')', ']', '}' -> {
                indentLevel--
                stringBuilder.appendLine().append(padding()).append(char)
            }
            ',' -> {
                stringBuilder.appendLine(char).append(padding())
                // ignore space after comma as we have added a newline
                val nextChar = toString.getOrElse(i + 1) { char }
                if (nextChar == ' ') i++
            }
            else -> {
                stringBuilder.append(char)
            }
        }
        i++
    }

    return stringBuilder.toString()
}
