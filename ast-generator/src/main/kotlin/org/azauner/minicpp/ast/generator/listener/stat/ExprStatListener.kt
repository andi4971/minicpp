package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class ExprStatListener(private val exprListener: ExprListener): minicppBaseListener() {

    private var exprStats = mutableListOf<org.azauner.minicpp.ast.node.ExprStat>()

    override fun exitExprStat(ctx: minicppParser.ExprStatContext) {
        exprStats.add(org.azauner.minicpp.ast.node.ExprStat(ctx.expr().let { exprListener.getExpr() }))
    }

    fun getExprStat(): org.azauner.minicpp.ast.node.ExprStat {
        return exprStats.removeLast()
    }
}
