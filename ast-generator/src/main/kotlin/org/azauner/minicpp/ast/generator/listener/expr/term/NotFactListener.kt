package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class NotFactListener(private val factListener: FactListener): minicppBaseListener() {

    private var notFacts = mutableListOf<org.azauner.minicpp.ast.node.NotFact>()

    override fun exitNotFact(ctx: minicppParser.NotFactContext) {
        notFacts.add(org.azauner.minicpp.ast.node.NotFact(negated = ctx.NOT() != null, fact = factListener.getFact()))
    }

    fun getNotFact(): org.azauner.minicpp.ast.node.NotFact {
        return notFacts.removeLast()
    }
}
