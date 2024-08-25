package org.azauner.ast.generator.visitor.expr

import org.azauner.ast.generator.visitor.expr.term.TermVisitor
import org.azauner.ast.node.SimpleExpr
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class SimpleExprVisitor(private val scope: Scope) : minicppBaseVisitor<SimpleExpr>() {

    override fun visitSimpleExpr(ctx: minicppParser.SimpleExprContext): SimpleExpr {
        return SimpleExpr(
            sign = ctx.SIGN()?.accept(SignVisitor()),
            term = ctx.term().accept(TermVisitor(scope)),
            simpleExprEntries = ctx.simpleExprEntry().map { it.accept(SimpleExprEntryVisitor(scope)) }
        )
    }
}
