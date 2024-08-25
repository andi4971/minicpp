package org.azauner.ast.generator.visitor.expr

import org.azauner.ast.node.RelExpr
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class RelExprVisitor(private val scope: Scope) : minicppBaseVisitor<RelExpr>() {

    override fun visitRelExpr(ctx: minicppParser.RelExprContext): RelExpr {
        return RelExpr(
            firstExpr = ctx.simpleExpr().accept(SimpleExprVisitor(scope)),
            relExprEntries = ctx.relExprEntry().map { it.accept(RelExprEntryVisitor(scope)) }
        )
    }
}
