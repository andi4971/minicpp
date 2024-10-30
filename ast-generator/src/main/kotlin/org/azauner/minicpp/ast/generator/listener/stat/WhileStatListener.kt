package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.minicpp.ast.node.WhileStat
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class WhileStatListener(private val exprListener: ExprListener, private val statListener: StatListener) :
    minicppBaseListener() {

    private var whileStats = Collections.synchronizedList(mutableListOf<WhileStat>())

    override fun exitWhileStat(ctx: minicppParser.WhileStatContext) {
        whileStats.add(WhileStat(exprListener.getExpr(), statListener.getStat()))
    }

    fun getWhileStat(): WhileStat {
        return whileStats.removeLast()
    }
}
