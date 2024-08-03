package org.azauner.ast.generator

import org.antlr.runtime.ANTLRInputStream
import org.antlr.v4.runtime.BufferedTokenStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.TokenStream
import org.azauner.parser.minicppLexer
import org.azauner.parser.minicppParser
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val fileName = "Sieve.mcpp"
    val inputStream = ClassLoader.getSystemResourceAsStream(fileName)

    val charStream = CharStreams.fromStream(inputStream)
    val lexer = minicppLexer(charStream)
    val tokenStream = BufferedTokenStream(lexer)
    val parser = minicppParser(tokenStream)
    parser.miniCpp()
}
