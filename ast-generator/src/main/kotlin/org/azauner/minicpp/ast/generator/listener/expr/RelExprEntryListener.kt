package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.node.RelExprEntry
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class RelExprEntryListener(
    private val simpleExprListener: SimpleExprListener,
    private val relOperatorListener: RelOperatorListener
) : minicppBaseListener() {

    private var relExprEntries = Collections.synchronizedList(mutableListOf<RelExprEntry>())

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
}
