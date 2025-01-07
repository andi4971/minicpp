package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class WhileStatListener(private val exprListener: ExprListener, private val statListener: StatListener) :
    minicppBaseListener() {

    private var whileStats = mutableListOf<org.azauner.minicpp.ast.node.WhileStat>()

    override fun exitWhileStat(ctx: minicppParser.WhileStatContext) {
        whileStats.add(org.azauner.minicpp.ast.node.WhileStat(exprListener.getExpr(), statListener.getStat()))
    }

    fun getWhileStat(): org.azauner.minicpp.ast.node.WhileStat {
        return whileStats.removeLast()
    }
}
