package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.util.ScopeHandler
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class OrExprListener(private val andExprListener: AndExprListener,
                     private val scopeHandler: ScopeHandler
) : minicppBaseListener() {

    private var orExprs = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.OrExpr>())

    override fun exitOrExpr(ctx: minicppParser.OrExprContext) {
        val scope = scopeHandler.getScope()
        val entries = mutableListOf<org.azauner.minicpp.ast.node.AndExpr>()
        repeat(ctx.andExpr().size) {
            entries.add(andExprListener.getAndExpr())
        }
        orExprs.add(org.azauner.minicpp.ast.node.OrExpr(andExpressions = entries, scope))
    }

    fun getOrExpr(): org.azauner.minicpp.ast.node.OrExpr {
        return orExprs.removeLast()
    }
}
