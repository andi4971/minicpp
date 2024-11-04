package org.azauner.minicpp.ast.generator.visitor.expr

import org.azauner.minicpp.ast.util.validate
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class ExprVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.Expr>() {

    override fun visitExpr(ctx: minicppParser.ExprContext): org.azauner.minicpp.ast.node.Expr {
        return org.azauner.minicpp.ast.node.Expr(
            firstExpr = ctx.orExpr().accept(OrExprVisitor(scope)),
            exprEntries = ctx.exprEntry().map { it.accept(ExprEntryVisitor(scope)) },
            scope = scope
        ).also {
            it.validate()
        }

    }
}
