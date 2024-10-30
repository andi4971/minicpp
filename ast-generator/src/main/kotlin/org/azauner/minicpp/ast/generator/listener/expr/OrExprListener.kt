package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.node.AndExpr
import org.azauner.minicpp.ast.node.OrExpr
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class OrExprListener(private val andExprListener: AndExprListener): minicppBaseListener() {

    private var orExprs = Collections.synchronizedList(mutableListOf<OrExpr>())

    override fun exitOrExpr(ctx: minicppParser.OrExprContext) {
        val scope = Scope(null)
        val entries = mutableListOf<AndExpr>()
        repeat(ctx.andExpr().size) {
            entries.add(andExprListener.getAndExpr())
        }
        orExprs.add(OrExpr(andExpressions = entries, scope))
    }

    fun getOrExpr(): OrExpr {
        return orExprs.removeLast()
    }
}
