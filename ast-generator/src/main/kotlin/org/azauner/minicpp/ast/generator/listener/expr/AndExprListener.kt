package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.node.AndExpr
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class AndExprListener(private val relExprListener: RelExprListener): minicppBaseListener() {

    private var andExprs = mutableListOf<AndExpr>()

    override fun exitAndExpr(ctx: minicppParser.AndExprContext) {
        val entries = relExprListener.getRelExpr(ctx.relExpr().size)
        andExprs.add(AndExpr(entries))
    }

    fun getAndExpr(): AndExpr {
        return andExprs.removeLast()
    }

    fun getAndExpr(n: Int): List<AndExpr> {
        return andExprs.subList(andExprs.size - n, andExprs.size).also {
            andExprs.dropLast(n)
        }
    }
}
