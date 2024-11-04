package org.azauner.minicpp.ast.generator.visitor.expr

import org.azauner.minicpp.ast.generator.visitor.expr.term.TermVisitor
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class SimpleExprEntryVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.SimpleExprEntry>() {

    override fun visitSimpleExprEntry(ctx: minicppParser.SimpleExprEntryContext): org.azauner.minicpp.ast.node.SimpleExprEntry {
        return org.azauner.minicpp.ast.node.SimpleExprEntry(
            sign = ctx.SIGN().accept(SignVisitor()),
            term = ctx.term().accept(TermVisitor(scope))
        )
    }
}
