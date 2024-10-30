package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.minicpp.ast.node.Expr
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class ActParListListener(private val exprListener: ExprListener): minicppBaseListener() {

    private var actParLists = Collections.synchronizedList(mutableListOf<List<Expr>>())

    override fun exitActParList(ctx: minicppParser.ActParListContext) {
        val size = ctx.expr().size
        val exprs = mutableListOf<Expr>()
        repeat(size) {
            exprs.add(exprListener.getExpr())
        }
        actParLists.add(exprs)
    }

    fun getActParList(): List<Expr> {
        return actParLists.removeLast()
    }
}
