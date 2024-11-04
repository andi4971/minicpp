package org.azauner.minicpp.ast.generator.visitor.expr

import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class RelExprEntryVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.RelExprEntry>() {

    override fun visitRelExprEntry(ctx: minicppParser.RelExprEntryContext): org.azauner.minicpp.ast.node.RelExprEntry {
        return org.azauner.minicpp.ast.node.RelExprEntry(
            simpleExpr = ctx.simpleExpr().accept(SimpleExprVisitor(scope)),
            relOperator = ctx.relOperator().accept(RelOperatorVisitor())
        )
    }
}
