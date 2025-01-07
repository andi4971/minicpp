package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class BreakStatListener: minicppBaseListener() {

    private var breakStats = mutableListOf<org.azauner.minicpp.ast.node.BreakStat>()

    override fun exitBreakStat(ctx: minicppParser.BreakStatContext) {
        breakStats.add(org.azauner.minicpp.ast.node.BreakStat)
    }

    fun getBreakStat(): org.azauner.minicpp.ast.node.BreakStat {
        return breakStats.removeLast()
    }
}
