package org.azauner.ast.generator

import org.antlr.v4.runtime.BufferedTokenStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.azauner.ast.generator.visitor.MiniCppVisitor
import org.azauner.parser.minicppLexer
import org.azauner.parser.minicppParser

fun main() {
    val fileName = "Sieve.mcpp"
    val inputStream = ClassLoader.getSystemResourceAsStream(fileName)

    val charStream = CharStreams.fromStream(inputStream)
    val lexer = minicppLexer(charStream)
    val tokenStream = BufferedTokenStream(lexer)
    val parser = minicppParser(tokenStream)
    val walker = ParseTreeWalker()

    //walker.walk(MiniCppListener(parser), parser.miniCpp())
    val result = MiniCppVisitor().visit(parser.miniCpp())
    println(result)
}
