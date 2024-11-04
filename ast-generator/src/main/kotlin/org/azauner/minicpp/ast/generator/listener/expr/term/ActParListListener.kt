package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class ActParListListener(private val exprListener: ExprListener): minicppBaseListener() {

    private var actParLists = Collections.synchronizedList(mutableListOf<List<org.azauner.minicpp.ast.node.Expr>>())

    override fun exitActParList(ctx: minicppParser.ActParListContext) {
        val size = ctx.expr().size
        val exprs = mutableListOf<org.azauner.minicpp.ast.node.Expr>()
        repeat(size) {
            exprs.add(exprListener.getExpr())
        }
        actParLists.add(exprs)
    }

    fun getActParList(): List<org.azauner.minicpp.ast.node.Expr> {
        return actParLists.removeLast()
    }
}
