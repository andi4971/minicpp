package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class EmptyStatListener: minicppBaseListener() {

    private var emptyStats = mutableListOf<org.azauner.minicpp.ast.node.EmptyStat>()

    override fun exitEmptyStat(ctx: minicppParser.EmptyStatContext) {
        emptyStats.add(org.azauner.minicpp.ast.node.EmptyStat)
    }

    fun getEmptyStat(): org.azauner.minicpp.ast.node.EmptyStat {
        return emptyStats.removeLast()
    }
}
