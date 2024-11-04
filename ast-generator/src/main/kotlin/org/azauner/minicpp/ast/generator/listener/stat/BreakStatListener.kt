package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class BreakStatListener: minicppBaseListener() {

    private var breakStats = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.BreakStat>())

    override fun exitBreakStat(ctx: minicppParser.BreakStatContext) {
        breakStats.add(org.azauner.minicpp.ast.node.BreakStat)
    }

    fun getBreakStat(): org.azauner.minicpp.ast.node.BreakStat {
        return breakStats.removeLast()
    }
}
