package org.azauner.ast.generator

import org.antlr.v4.runtime.BufferedTokenStream
import org.antlr.v4.runtime.CharStreams
import org.azauner.ast.generator.visitor.MiniCppVisitor
import org.azauner.ast.node.MiniCpp
import org.azauner.parser.minicppLexer
import org.azauner.parser.minicppParser
import java.io.InputStream

fun generateASTForFile(inputStream: InputStream, className: String): MiniCpp {
    val charStream = CharStreams.fromStream(inputStream)
    val lexer = minicppLexer(charStream)
    val tokenStream = BufferedTokenStream(lexer)
    val parser = minicppParser(tokenStream)
    return MiniCppVisitor(className).visit(parser.miniCpp())
}
