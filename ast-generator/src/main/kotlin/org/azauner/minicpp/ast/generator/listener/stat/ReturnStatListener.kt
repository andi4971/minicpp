package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.minicpp.ast.node.ReturnStat
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class ReturnStatListener(private val exprListener: ExprListener): minicppBaseListener() {

    private var returnStats = Collections.synchronizedList(mutableListOf<ReturnStat>())

    override fun exitReturnStat(ctx: minicppParser.ReturnStatContext) {
        returnStats.add(ReturnStat(ctx.expr()?.let { exprListener.getExpr() }))
    }

    fun getReturnStat(): ReturnStat {
        return returnStats.removeLast()
    }
}
