package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.minicpp.ast.node.IfStat
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class IfStatListener(private val exprListener: ExprListener, private val statListener: StatListener) :
    minicppBaseListener() {

    private var ifStats = Collections.synchronizedList(mutableListOf<IfStat>())

    override fun exitIfStat(ctx: minicppParser.IfStatContext) {
        val elseStat = ctx.elseStat()?.let { statListener.getStat() }
        //TODO check if order of stats needs to be switchd
        ifStats.add(IfStat(condition = exprListener.getExpr(), thenStat = statListener.getStat(), elseStat = elseStat))
    }

    fun getIfStat(): IfStat {
        return ifStats.removeLast()
    }
}
