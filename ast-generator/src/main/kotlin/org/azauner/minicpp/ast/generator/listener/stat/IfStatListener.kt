package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class IfStatListener(private val exprListener: ExprListener, private val statListener: StatListener) :
    minicppBaseListener() {

    private var ifStats = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.IfStat>())

    override fun exitIfStat(ctx: minicppParser.IfStatContext) {
        val elseStat = ctx.elseStat()?.let { statListener.getStat() }
        ifStats.add(
            org.azauner.minicpp.ast.node.IfStat(
                condition = exprListener.getExpr(),
                thenStat = statListener.getStat(),
                elseStat = elseStat
            )
        )
    }

    fun getIfStat(): org.azauner.minicpp.ast.node.IfStat {
        return ifStats.removeLast()
    }
}
