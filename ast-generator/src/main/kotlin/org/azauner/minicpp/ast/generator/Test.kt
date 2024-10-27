package org.azauner.minicpp.ast.generator

fun main() {
    val fileName = "Sieve.mcpp"
    val inputStream = ClassLoader.getSystemResourceAsStream(fileName)
    val className = fileName.substringBefore(".mcpp")


    //walker.walk(MiniCppListener(parser), parser.miniCpp())
    //val result = generateASTForFileVisitor(inputStream, className)
    val result = generateAstForFileListener(inputStream, className)
    println(result.prettyPrint())
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
