package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class TermOperatorListener: minicppBaseListener() {

    private var termOperators = mutableListOf<org.azauner.minicpp.ast.node.TermOperator>()

    override fun exitStarOperator(ctx: minicppParser.StarOperatorContext?) {
        termOperators.add(org.azauner.minicpp.ast.node.TermOperator.MUL)
    }

    override fun exitDivOperator(ctx: minicppParser.DivOperatorContext?) {
        termOperators.add(org.azauner.minicpp.ast.node.TermOperator.DIV)
    }

    override fun exitModOperator(ctx: minicppParser.ModOperatorContext?) {
        termOperators.add(org.azauner.minicpp.ast.node.TermOperator.MOD)
    }

    fun getTermOperator(): org.azauner.minicpp.ast.node.TermOperator {
        return termOperators.removeLast()
    }
}
