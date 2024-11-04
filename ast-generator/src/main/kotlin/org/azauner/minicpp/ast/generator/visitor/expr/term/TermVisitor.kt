package org.azauner.minicpp.ast.generator.visitor.expr.term

import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class TermVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.Term>() {

    override fun visitTerm(ctx: minicppParser.TermContext): org.azauner.minicpp.ast.node.Term {
        return org.azauner.minicpp.ast.node.Term(
            firstNotFact = ctx.notFact().accept(NotFactVisitor(scope)),
            termEntries = ctx.termEntry().map { it.accept(TermEntryVisitor(scope)) }
        )
    }
}
