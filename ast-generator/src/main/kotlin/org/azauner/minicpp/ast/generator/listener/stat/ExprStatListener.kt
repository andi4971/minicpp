package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.minicpp.ast.node.ExprStat
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class ExprStatListener(private val exprListener: ExprListener): minicppBaseListener() {

    private var exprStats = Collections.synchronizedList(mutableListOf<ExprStat>())

    override fun exitExprStat(ctx: minicppParser.ExprStatContext) {
        exprStats.add(ExprStat(ctx.expr().let { exprListener.getExpr() }))
    }

    fun getExprStat(): ExprStat {
        return exprStats.removeLast()
    }
}
