package org.azauner.ast.generator.visitor.expr

import org.azauner.ast.node.OrExpr
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class OrExprVisitor(private val scope: Scope) : minicppBaseVisitor<OrExpr>() {

    override fun visitOrExpr(ctx: minicppParser.OrExprContext): OrExpr {
        val orExpr =  OrExpr(andExpressions = ctx.andExpr().map { it.accept(AndExprVisitor(scope)) })
        return orExpr
    }
}
