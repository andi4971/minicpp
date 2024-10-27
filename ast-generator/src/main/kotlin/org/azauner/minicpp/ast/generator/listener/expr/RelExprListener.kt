package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.node.RelExpr
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class RelExprListener(private val simpleExprListener: SimpleExprListener,
    private val relExprEntryListener: RelExprEntryListener): minicppBaseListener() {

    private var relExprs = mutableListOf<RelExpr>()

    override fun exitRelExpr(ctx: minicppParser.RelExprContext) {
        val entries = relExprEntryListener.getRelExprEntry(ctx.relExprEntry().size)
        relExprs.add(RelExpr(
            firstExpr = simpleExprListener.getSimpleExpr(),
            relExprEntries = entries
            ))
    }

    fun getRelExpr(): RelExpr {
        return relExprs.removeLast()
    }

    fun getRelExpr(n: Int): List<RelExpr> {
        return relExprs.subList(relExprs.size - n, relExprs.size).also {
            relExprs.dropLast(n)
        }
    }
}
