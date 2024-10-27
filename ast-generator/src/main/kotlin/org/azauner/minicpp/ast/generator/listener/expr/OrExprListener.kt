package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.node.OrExpr
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class OrExprListener(private val andExprListener: AndExprListener): minicppBaseListener() {

    private var orExprs = mutableListOf<OrExpr>()

    override fun exitOrExpr(ctx: minicppParser.OrExprContext) {
        val scope = Scope(null)
        val entries = andExprListener.getAndExpr(ctx.andExpr().size)
        orExprs.add(OrExpr(andExpressions = entries, scope))
    }

    fun getOrExpr(): OrExpr {
        return orExprs.removeLast()
    }
}
