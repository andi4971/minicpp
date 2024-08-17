package org.azauner.ast.generator.visitor.expr

import org.azauner.ast.generator.visitor.expr.term.TermVisitor
import org.azauner.ast.node.SimpleExprEntry
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class SimpleExprEntryVisitor: minicppBaseVisitor<SimpleExprEntry>() {

    override fun visitSimpleExprEntry(ctx: minicppParser.SimpleExprEntryContext): SimpleExprEntry {
        return SimpleExprEntry(
            sign = ctx.SIGN().accept(SignVisitor()),
            term = ctx.term().accept(TermVisitor())
        )
    }
}
