package org.azauner.ast.generator.visitor.expr

import org.azauner.ast.node.RelExprEntry
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class RelExprEntryVisitor(private val scope: Scope) : minicppBaseVisitor<RelExprEntry>() {

    override fun visitRelExprEntry(ctx: minicppParser.RelExprEntryContext): RelExprEntry {
        return RelExprEntry(
            simpleExpr = ctx.simpleExpr().accept(SimpleExprVisitor(scope)),
            relOperator = ctx.relOperator().accept(RelOperatorVisitor())
        )
    }
}
