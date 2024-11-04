package org.azauner.minicpp.ast.generator.visitor.expr

import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class RelExprVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.RelExpr>() {

    override fun visitRelExpr(ctx: minicppParser.RelExprContext): org.azauner.minicpp.ast.node.RelExpr {
        return org.azauner.minicpp.ast.node.RelExpr(
            firstExpr = ctx.simpleExpr().accept(SimpleExprVisitor(scope)),
            relExprEntries = ctx.relExprEntry().map { it.accept(RelExprEntryVisitor(scope)) }
        )
    }
}
