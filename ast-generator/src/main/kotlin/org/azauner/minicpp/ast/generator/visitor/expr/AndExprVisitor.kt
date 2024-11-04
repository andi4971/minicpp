package org.azauner.minicpp.ast.generator.visitor.expr

import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class AndExprVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.AndExpr>() {

    override fun visitAndExpr(ctx: minicppParser.AndExprContext): org.azauner.minicpp.ast.node.AndExpr {
        return org.azauner.minicpp.ast.node.AndExpr(ctx.relExpr().map { it.accept(RelExprVisitor(scope)) })
    }
}
