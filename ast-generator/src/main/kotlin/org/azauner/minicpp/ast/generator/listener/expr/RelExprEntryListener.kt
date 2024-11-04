package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class RelExprEntryListener(
    private val simpleExprListener: SimpleExprListener,
    private val relOperatorListener: RelOperatorListener
) : minicppBaseListener() {

    private var relExprEntries =
        Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.RelExprEntry>())

    override fun exitRelExprEntry(ctx: minicppParser.RelExprEntryContext) {
        relExprEntries.add(
            org.azauner.minicpp.ast.node.RelExprEntry(
                simpleExpr = simpleExprListener.getSimpleExpr(),
                relOperator = relOperatorListener.getRelOperator()
            )
        )
    }

    fun getRelExprEntry(): org.azauner.minicpp.ast.node.RelExprEntry {
        return relExprEntries.removeLast()
    }
}
