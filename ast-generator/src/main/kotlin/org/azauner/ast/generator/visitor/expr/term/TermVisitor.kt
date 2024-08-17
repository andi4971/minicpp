package org.azauner.ast.generator.visitor.expr.term

import org.azauner.ast.node.Term
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class TermVisitor: minicppBaseVisitor<Term>() {

    override fun visitTerm(ctx: minicppParser.TermContext): Term {
        return Term(
            firstFactor = ctx.factor().accept(FactorVisitor()),
            termEntries = ctx.termEntry().map { it.accept(TermEntryVisitor()) }
        )
    }
}
