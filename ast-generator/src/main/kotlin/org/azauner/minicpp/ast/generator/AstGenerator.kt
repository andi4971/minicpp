package org.azauner.minicpp.ast.generator

import org.antlr.v4.runtime.BufferedTokenStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.tree.ParseTreeListener
import org.azauner.minicpp.ast.generator.listener.MiniCppEntryListener
import org.azauner.minicpp.ast.generator.listener.MiniCppListener
import org.azauner.minicpp.ast.generator.listener.field.*
import org.azauner.minicpp.ast.generator.listener.func.FormParListEntriesListener
import org.azauner.minicpp.ast.generator.listener.func.FormParListListener
import org.azauner.minicpp.ast.generator.listener.func.FuncDeclListener
import org.azauner.minicpp.ast.generator.listener.func.FuncHeadListener
import org.azauner.minicpp.ast.generator.visitor.MiniCppVisitor
import org.azauner.minicpp.ast.node.MiniCpp
import org.azauner.parser.minicppLexer
import org.azauner.parser.minicppParser
import java.io.InputStream

fun generateASTForFileVisitor(inputStream: InputStream, className: String): MiniCpp {
    val charStream = CharStreams.fromStream(inputStream)
    val lexer = minicppLexer(charStream)
    val tokenStream = BufferedTokenStream(lexer)
    val parser = minicppParser(tokenStream)
    return MiniCppVisitor(className).visit(parser.miniCpp())
}

fun generateAstForFileListener(inputStream: InputStream, className: String): MiniCpp {
    val charStream = CharStreams.fromStream(inputStream)
    val lexer = minicppLexer(charStream)
    val tokenStream = BufferedTokenStream(lexer)
    val parser = minicppParser(tokenStream)


    val listeners  =createListeners()
    listeners.forEach {
        parser.addParseListener(it)
    }
    val miniCppListener = listeners.filterIsInstance<MiniCppListener>().first()
    parser.miniCpp()
    return miniCppListener.result
}

private fun createListeners(): List<ParseTreeListener> {
    val typeListener = TypeListener()
    val initListener = InitListener()

    val constDefEntryListener = ConstDefEntryListener(initListener)
    val constDefListener = ConstDefListener(typeListener, constDefEntryListener)

    val varDefEntryListener = VarDefEntryListener(initListener)
    val varDefListener = VarDefListener(typeListener, varDefEntryListener)

    val formParListEntryListener = FormParListEntriesListener(typeListener)
    val formParListListener = FormParListListener(formParListEntryListener)
    val funcHeadListener = FuncHeadListener(typeListener,formParListListener )
    val funcDeclListener = FuncDeclListener(funcHeadListener)

    val miniCppEntryListener = MiniCppEntryListener(constDefListener, varDefListener, funcDeclListener)
    val miniCppListener = MiniCppListener(miniCppEntryListener)
    return listOf(
        typeListener,
        initListener,
        constDefEntryListener,
        constDefListener,
        varDefEntryListener,
        varDefListener,
        formParListEntryListener,
        formParListListener,
        funcHeadListener,
        funcDeclListener,
        miniCppEntryListener,
        miniCppListener
    )
}
