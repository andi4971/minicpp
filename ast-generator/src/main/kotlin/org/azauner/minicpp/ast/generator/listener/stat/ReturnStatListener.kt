package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.node.ReturnStat
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class ReturnStatListener: minicppBaseListener() {

    private var returnStats = mutableListOf<ReturnStat>()

    override fun exitReturnStat(ctx: minicppParser.ReturnStatContext) {
        returnStats.add(ReturnStat())
    }

    fun getReturnStat(): ReturnStat {
        return returnStats.removeLast()
    }
}
