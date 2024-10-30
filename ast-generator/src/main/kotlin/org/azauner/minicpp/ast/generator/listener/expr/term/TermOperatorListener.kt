package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.node.TermOperator
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class TermOperatorListener: minicppBaseListener() {

    private var termOperators = Collections.synchronizedList(mutableListOf<TermOperator>())

    override fun exitStarOperator(ctx: minicppParser.StarOperatorContext?) {
        termOperators.add(TermOperator.MUL)
    }

    override fun exitDivOperator(ctx: minicppParser.DivOperatorContext?) {
        termOperators.add(TermOperator.DIV)
    }

    override fun exitModOperator(ctx: minicppParser.ModOperatorContext?) {
        termOperators.add(TermOperator.MOD)
    }

    fun getTermOperator(): TermOperator {
        return termOperators.removeLast()
    }
}
