package org.azauner.ast.generator.visitor.expr

import org.azauner.ast.node.RelExprEntry
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class RelExprEntryVisitor: minicppBaseVisitor<RelExprEntry>() {

    override fun visitRelExprEntry(ctx: minicppParser.RelExprEntryContext): RelExprEntry {
        return RelExprEntry(
            simpleExpr = ctx.simpleExpr().accept(SimpleExprVisitor()),
            relOperator = ctx.relOperator().accept(RelOperatorVisitor())
        )
    }
}
