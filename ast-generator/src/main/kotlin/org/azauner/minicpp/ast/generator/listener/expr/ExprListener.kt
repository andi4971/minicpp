package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.node.Expr
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class ExprListener(private val orExprListener: OrExprListener, private val exprEntryListener: ExprEntryListener) :
    minicppBaseListener() {

    private val exprs = mutableListOf<Expr>()

    override fun exitExpr(ctx: minicppParser.ExprContext) {
        val entries = exprEntryListener.getExprEntry(ctx.exprEntry().size)
        val scope = Scope(null)
        //TODO
        exprs.add(
            Expr(
                firstExpr = orExprListener.getOrExpr(),
                exprEntries = entries,
                scope = scope
            )
        )
    }

    fun getExpr(): Expr {
        return exprs.removeLast()
    }

    fun getExpr(n: Int): List<Expr> {
        return exprs.subList(exprs.size - n, exprs.size).also {
            exprs.dropLast(n)
        }
    }
}
