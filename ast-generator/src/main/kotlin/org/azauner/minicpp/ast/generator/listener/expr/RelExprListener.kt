package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class RelExprListener(private val simpleExprListener: SimpleExprListener,
    private val relExprEntryListener: RelExprEntryListener): minicppBaseListener() {

    private var relExprs = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.RelExpr>())

    override fun exitRelExpr(ctx: minicppParser.RelExprContext) {
        val entries = mutableListOf<org.azauner.minicpp.ast.node.RelExprEntry>()
        repeat(ctx.relExprEntry().size) {
            entries.add(relExprEntryListener.getRelExprEntry())
        }
        relExprs.add(
            org.azauner.minicpp.ast.node.RelExpr(
                firstExpr = simpleExprListener.getSimpleExpr(),
                relExprEntries = entries.reversed()
            )
        )
    }

    fun getRelExpr(): org.azauner.minicpp.ast.node.RelExpr {
        return relExprs.removeLast()
    }
}
