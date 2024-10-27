package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.node.EmptyStat
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class EmptyStatListener: minicppBaseListener() {

    private var emptyStats = mutableListOf<EmptyStat>()

    override fun exitEmptyStat(ctx: minicppParser.EmptyStatContext) {
        emptyStats.add(EmptyStat)
    }

    fun getEmptyStat(): EmptyStat {
        return emptyStats.removeLast()
    }
}
