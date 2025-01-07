package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class ReturnStatListener(private val exprListener: ExprListener): minicppBaseListener() {

    private var returnStats = mutableListOf<org.azauner.minicpp.ast.node.ReturnStat>()

    override fun exitReturnStat(ctx: minicppParser.ReturnStatContext) {
        returnStats.add(org.azauner.minicpp.ast.node.ReturnStat(ctx.expr()?.let { exprListener.getExpr() }))
    }

    fun getReturnStat(): org.azauner.minicpp.ast.node.ReturnStat {
        return returnStats.removeLast()
    }
}
