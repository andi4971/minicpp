package org.azauner.minicpp.ast.generator.visitor.expr.term

import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class NotFactVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.NotFact>() {

    override fun visitNotFact(ctx: minicppParser.NotFactContext): org.azauner.minicpp.ast.node.NotFact {
        return org.azauner.minicpp.ast.node.NotFact(
            negated = ctx.NOT() != null,
            fact = ctx.fact().accept(FactVisitor(scope))
        )
    }
}
