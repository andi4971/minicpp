package org.azauner.minicpp.ast.generator.visitor.expr.term

import org.azauner.minicpp.ast.node.NotFact
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class NotFactVisitor(private val scope: Scope) : minicppBaseVisitor<NotFact>() {

    override fun visitNotFact(ctx: minicppParser.NotFactContext): NotFact {
        return NotFact(
            negated = ctx.NOT() != null,
            //TODO
            fact = ctx.fact().accept(FactVisitor(scope, false))
        )
    }
}
