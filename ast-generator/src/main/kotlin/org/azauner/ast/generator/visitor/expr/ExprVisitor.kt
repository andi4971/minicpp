package org.azauner.ast.generator.visitor.expr

import org.azauner.ast.node.Expr
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class ExprVisitor: minicppBaseVisitor<Expr>() {

    override fun visitExpr(ctx: minicppParser.ExprContext): Expr {
        return Expr(firstExpr = ctx.orExpr().accept(OrExprVisitor())
        , exprEntries = ctx.exprEntry().map { it.accept(ExprEntryVisitor()) })
    }
}
