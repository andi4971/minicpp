package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.node.RelExprEntry
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class RelExprEntryListener(
    private val simpleExprListener: SimpleExprListener,
    private val relOperatorListener: RelOperatorListener
) : minicppBaseListener() {

    private var relExprEntries = mutableListOf<RelExprEntry>()

    override fun exitRelExprEntry(ctx: minicppParser.RelExprEntryContext) {
        relExprEntries.add(
            RelExprEntry(
                simpleExpr = simpleExprListener.getSimpleExpr(),
                relOperator = relOperatorListener.getRelOperator()
            )
        )
    }

    fun getRelExprEntry(): RelExprEntry {
        return relExprEntries.removeLast()
    }

    fun getRelExprEntry(n: Int): List<RelExprEntry> {
        return relExprEntries.subList(relExprEntries.size - n, relExprEntries.size).also {
            relExprEntries.dropLast(n)
        }
    }
}
