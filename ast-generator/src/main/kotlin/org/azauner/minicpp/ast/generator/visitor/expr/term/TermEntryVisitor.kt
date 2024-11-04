package org.azauner.minicpp.ast.generator.visitor.expr.term
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class TermEntryVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.TermEntry>() {

    override fun visitTermEntry(ctx: minicppParser.TermEntryContext): org.azauner.minicpp.ast.node.TermEntry {
        return org.azauner.minicpp.ast.node.TermEntry(
            termOperator = ctx.termOperator().accept(TermOperatorVisitor()),
            notFact = ctx.notFact().accept(NotFactVisitor(scope)),
        )
    }
}
