package org.azauner.minicpp.ast.generator.visitor.expr

import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class OrExprVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.OrExpr>() {

    override fun visitOrExpr(ctx: minicppParser.OrExprContext): org.azauner.minicpp.ast.node.OrExpr {
        val orExpr = org.azauner.minicpp.ast.node.OrExpr(
            andExpressions = ctx.andExpr().map { it.accept(AndExprVisitor(scope)) },
            scope = scope
        )
        return orExpr
    }
}
