package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.generator.listener.ScopeHandler
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class ExprListener(private val scopeHandler: ScopeHandler) :
    minicppBaseListener() {

    fun initListeners(orExprListener: OrExprListener, exprEntryListener: ExprEntryListener) {
        this.orExprListener = orExprListener
        this.exprEntryListener = exprEntryListener
    }

    private lateinit var orExprListener: OrExprListener
    private lateinit var exprEntryListener: ExprEntryListener

    private val exprs = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.Expr>())

    private var addedExpr = 0
    private var removedExpr = 0

    override fun exitExpr(ctx: minicppParser.ExprContext) {
        val entries = exprEntryListener.getExprEntry(ctx.exprEntry().size)
        val scope = scopeHandler.getScope()
        addedExpr++
        exprs.add(
            org.azauner.minicpp.ast.node.Expr(
                firstExpr = orExprListener.getOrExpr(),
                exprEntries = entries,
                scope = scope
            )
        )


    }

    fun getExpr(): org.azauner.minicpp.ast.node.Expr {
        removedExpr++
        return exprs.removeLast()
    }

}
