package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.minicpp.ast.node.Expr
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class ActParListListener(private val exprListener: ExprListener): minicppBaseListener() {

    private var actParLists = mutableListOf<List<Expr>>()

    override fun exitActParList(ctx: minicppParser.ActParListContext) {
        actParLists.add(exprListener.getExpr(ctx.expr().size))
    }

    fun getActParList(): List<Expr> {
        return actParLists.removeLast()
    }
}
