package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class AndExprListener(private val relExprListener: RelExprListener): minicppBaseListener() {

    private var andExprs = mutableListOf<org.azauner.minicpp.ast.node.AndExpr>()

    override fun exitAndExpr(ctx: minicppParser.AndExprContext) {
        val entries = mutableListOf<org.azauner.minicpp.ast.node.RelExpr>()
        repeat(ctx.relExpr().size) {
            entries.add(relExprListener.getRelExpr())
        }
        andExprs.add(org.azauner.minicpp.ast.node.AndExpr(entries.reversed()))
    }

    fun getAndExpr(): org.azauner.minicpp.ast.node.AndExpr {
        return andExprs.removeLast()
    }
}
