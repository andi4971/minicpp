package org.azauner.ast.generator.visitor.expr.term

import org.azauner.ast.node.Term
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class TermVisitor(private val scope: Scope) : minicppBaseVisitor<Term>() {

    override fun visitTerm(ctx: minicppParser.TermContext): Term {
        return Term(
            firstNotFact = ctx.notFact().accept(NotFactVisitor(scope)),
            termEntries = ctx.termEntry().map { it.accept(TermEntryVisitor(scope)) }
        )
    }
}