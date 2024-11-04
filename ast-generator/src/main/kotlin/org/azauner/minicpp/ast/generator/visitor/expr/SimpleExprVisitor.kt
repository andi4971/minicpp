package org.azauner.minicpp.ast.generator.visitor.expr

import org.azauner.minicpp.ast.generator.visitor.expr.term.TermVisitor
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class SimpleExprVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.SimpleExpr>() {

    override fun visitSimpleExpr(ctx: minicppParser.SimpleExprContext): org.azauner.minicpp.ast.node.SimpleExpr {
        return org.azauner.minicpp.ast.node.SimpleExpr(
            sign = ctx.SIGN()?.accept(SignVisitor()),
            term = ctx.term().accept(TermVisitor(scope)),
            simpleExprEntries = ctx.simpleExprEntry().map { it.accept(SimpleExprEntryVisitor(scope)) }
        )
    }
}
