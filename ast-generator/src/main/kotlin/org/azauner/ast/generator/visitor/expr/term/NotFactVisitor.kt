package org.azauner.ast.generator.visitor.expr.term

import org.azauner.ast.node.NotFact
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class NotFactVisitor: minicppBaseVisitor<NotFact>() {

    override fun visitNotFact(ctx: minicppParser.NotFactContext): NotFact {
        return NotFact(
            negated = ctx.NOT() != null,
            fact = ctx.fact().accept(FactVisitor())
        )
    }
}
