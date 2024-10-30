package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.node.RelExpr
import org.azauner.minicpp.ast.node.RelExprEntry
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class RelExprListener(private val simpleExprListener: SimpleExprListener,
    private val relExprEntryListener: RelExprEntryListener): minicppBaseListener() {

    private var relExprs = Collections.synchronizedList(mutableListOf<RelExpr>())

    override fun exitRelExpr(ctx: minicppParser.RelExprContext) {
        val entries = mutableListOf<RelExprEntry>()
        repeat(ctx.relExprEntry().size) {
            entries.add(relExprEntryListener.getRelExprEntry())
        }
        relExprs.add(RelExpr(
            firstExpr = simpleExprListener.getSimpleExpr(),
            relExprEntries = entries
            ))
    }

    fun getRelExpr(): RelExpr {
        return relExprs.removeLast()
    }
}
