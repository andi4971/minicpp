package org.azauner.minicpp.ast.generator.visitor.expr

import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class ExprEntryVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.ExprEntry>() {

    override fun visitExprEntry(ctx: minicppParser.ExprEntryContext): org.azauner.minicpp.ast.node.ExprEntry {
        return org.azauner.minicpp.ast.node.ExprEntry(
            orExpr = ctx.orExpr().accept(OrExprVisitor(scope)),
            assignOperator = ctx.exprAssign().accept(ExprAssignVisitor())
        )
    }
}
