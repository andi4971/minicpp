package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.node.AndExpr
import org.azauner.minicpp.ast.node.RelExpr
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class AndExprListener(private val relExprListener: RelExprListener): minicppBaseListener() {

    private var andExprs = Collections.synchronizedList(mutableListOf<AndExpr>())

    override fun exitAndExpr(ctx: minicppParser.AndExprContext) {
        val entries = mutableListOf<RelExpr>()
        repeat(ctx.relExpr().size) {
            entries.add(relExprListener.getRelExpr())
        }
        andExprs.add(AndExpr(entries))
    }

    fun getAndExpr(): AndExpr {
        return andExprs.removeLast()
    }
}
