package org.azauner.ast.generator.visitor.expr

import org.azauner.ast.node.Expr
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class ExprVisitor(private val scope: Scope) : minicppBaseVisitor<Expr>() {

    override fun visitExpr(ctx: minicppParser.ExprContext): Expr {
        return Expr(firstExpr = ctx.orExpr().accept(OrExprVisitor(scope))
        , exprEntries = ctx.exprEntry().map { it.accept(ExprEntryVisitor(scope)) })
    }
}
