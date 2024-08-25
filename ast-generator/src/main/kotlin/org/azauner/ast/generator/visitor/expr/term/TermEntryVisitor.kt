package org.azauner.ast.generator.visitor.expr.term
import org.azauner.ast.node.TermEntry
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class TermEntryVisitor(private val scope: Scope) : minicppBaseVisitor<TermEntry>() {

    override fun visitTermEntry(ctx: minicppParser.TermEntryContext): TermEntry {
        return TermEntry(
            termOperator = ctx.termOperator().accept(TermOperatorVisitor()),
            notFact = ctx.notFact().accept(NotFactVisitor(scope)),
        )
    }
}
