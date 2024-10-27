package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.node.BreakStat
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class BreakStatListener: minicppBaseListener() {

    private var breakStats = mutableListOf<BreakStat>()

    override fun exitBreakStat(ctx: minicppParser.BreakStatContext) {
        breakStats.add(BreakStat)
    }

    fun getBreakStat(): BreakStat {
        return breakStats.removeLast()
    }
}
