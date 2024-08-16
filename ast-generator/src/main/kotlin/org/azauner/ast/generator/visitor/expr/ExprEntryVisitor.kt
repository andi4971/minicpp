package org.azauner.ast.generator.visitor.expr

import org.azauner.ast.node.ExprEntry
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class ExprEntryVisitor: minicppBaseVisitor<ExprEntry>() {

    override fun visitExprEntry(ctx: minicppParser.ExprEntryContext): ExprEntry {
        return ExprEntry(orExpr = ctx.orExpr().accept(OrExprVisitor()),
            assignOperator = ctx.exprAssign().accept(ExprAssignVisitor()))
    }
}
