package org.azauner.minicpp.ast.generator

import org.antlr.v4.runtime.BufferedTokenStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.tree.ParseTreeListener
import org.azauner.minicpp.ast.generator.listener.MiniCppEntryListener
import org.azauner.minicpp.ast.generator.listener.MiniCppListener
import org.azauner.minicpp.ast.generator.listener.ScopeHandler
import org.azauner.minicpp.ast.generator.listener.block.BlockEntryListener
import org.azauner.minicpp.ast.generator.listener.block.BlockListener
import org.azauner.minicpp.ast.generator.listener.expr.*
import org.azauner.minicpp.ast.generator.listener.expr.term.*
import org.azauner.minicpp.ast.generator.listener.field.*
import org.azauner.minicpp.ast.generator.listener.func.*
import org.azauner.minicpp.ast.generator.listener.stat.*
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

    val listeners = createListeners()
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
    val scopeHandler  = ScopeHandler()
    val constDefEntryListener = ConstDefEntryListener(initListener)
    val constDefListener = ConstDefListener(typeListener, constDefEntryListener, scopeHandler)

    val varDefEntryListener = VarDefEntryListener(initListener)
    val varDefListener = VarDefListener(typeListener, varDefEntryListener, scopeHandler)

    val formParListEntryListener = FormParListEntriesListener(typeListener)
    val formParListListener = FormParListListener(formParListEntryListener)
    val funcHeadListener = FuncHeadListener(typeListener, formParListListener)
    val funcDeclListener = FuncDeclListener(funcHeadListener)

    val exprListener = ExprListener(scopeHandler)

    val actParListListener = ActParListListener(exprListener)
    val callFactEntryOperationListener = CallFactEntryOperationListener(exprListener, actParListListener, scopeHandler)
    val callFactEntryListener = CallFactEntryListener(callFactEntryOperationListener, scopeHandler)
    val factListener = FactListener(callFactEntryListener, typeListener, exprListener)
    val notFactListener = NotFactListener(factListener)
    val termOperatorListener = TermOperatorListener()
    val termEntryListener = TermEntryListener(notFactListener, termOperatorListener)
    val termListener = TermListener(notFactListener, termEntryListener)
    val simpleExprEntryListener = SimpleExprEntryListener(termListener)
    val simpleExprListener = SimpleExprListener(termListener, simpleExprEntryListener)
    val relOperatorListener = RelOperatorListener()
    val relExprEntryListener = RelExprEntryListener(simpleExprListener, relOperatorListener)
    val relExprListener = RelExprListener(simpleExprListener, relExprEntryListener)
    val andExprListener = AndExprListener(relExprListener)
    val orExprListener = OrExprListener(andExprListener, scopeHandler)
    val assignOperatorListener = AssignOperatorListener()
    val exprEntryListener = ExprEntryListener(orExprListener, assignOperatorListener)
    exprListener.initListeners(orExprListener, exprEntryListener)

    val statListener = StatListener()

    val blockEntryListener = BlockEntryListener(statListener, varDefListener, constDefListener)
    val blockListener = BlockListener(blockEntryListener, scopeHandler)
    val funcDefListener = FuncDefListener(funcHeadListener, blockListener, scopeHandler)

    val whileStatListener = WhileStatListener(exprListener, statListener)
    val outputStatListener = OutputStatListener(exprListener)
    val breakStatListener = BreakStatListener()
    val inputStatListener = InputStatListener(scopeHandler)
    val deleteStatListener = DeleteStatListener(scopeHandler)
    val emptyStatListener = EmptyStatListener()
    val blockStatListener = BlockStatListener(blockListener)
    val returnStatListener = ReturnStatListener(exprListener)
    val exprStatListener = ExprStatListener(exprListener)
    val ifStatListener = IfStatListener(exprListener, statListener)
    statListener.initListeners(breakStatListener, inputStatListener, deleteStatListener, emptyStatListener,
        blockStatListener,
        returnStatListener,
        exprStatListener,
        whileStatListener,
        ifStatListener,
        outputStatListener
        )


    val miniCppEntryListener = MiniCppEntryListener(constDefListener, varDefListener, funcDeclListener, funcDefListener)
    val miniCppListener = MiniCppListener(miniCppEntryListener, scopeHandler)
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
        exprListener,
        actParListListener,
        callFactEntryOperationListener,
        callFactEntryListener,
        factListener,
        notFactListener,
        termOperatorListener,
        termEntryListener,
        termListener,
        simpleExprEntryListener,
        simpleExprListener,
        relOperatorListener,
        relExprEntryListener,
        relExprListener,
        andExprListener,
        orExprListener,
        assignOperatorListener,
        exprEntryListener,
        statListener,
        blockEntryListener,
        blockListener,
        funcDefListener,
        whileStatListener,
        outputStatListener,
        breakStatListener,
        inputStatListener,
        deleteStatListener,
        emptyStatListener,
        blockStatListener,
        returnStatListener,
        exprStatListener,
        ifStatListener,
        miniCppEntryListener,
        miniCppListener
    )
}
