package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.node.Expr
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class ExprListener :
    minicppBaseListener() {

    fun initListeners(orExprListener: OrExprListener, exprEntryListener: ExprEntryListener) {
        this.orExprListener = orExprListener
        this.exprEntryListener = exprEntryListener
    }

    private lateinit var orExprListener: OrExprListener
    private lateinit var exprEntryListener: ExprEntryListener

    private val exprs = Collections.synchronizedList(mutableListOf<Expr>())

    private var addedExpr = 0
    private var removedExpr = 0

    override fun exitExpr(ctx: minicppParser.ExprContext) {
        val entries = exprEntryListener.getExprEntry(ctx.exprEntry().size)
        val scope = Scope(null)
        //TODO
        addedExpr++
        exprs.add(
            Expr(
                firstExpr = orExprListener.getOrExpr(),
                exprEntries = entries,
                scope = scope
            )
        )


    }

    fun getExpr(): Expr {
        removedExpr++
        return exprs.removeLast()
    }

}