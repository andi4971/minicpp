package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.node.NotFact
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class NotFactListener(private val factListener: FactListener): minicppBaseListener() {

    private var notFacts = mutableListOf<NotFact>()

    override fun exitNotFact(ctx: minicppParser.NotFactContext) {
        notFacts.add(NotFact(negated = ctx.NOT() != null, fact = factListener.getFact()))
    }

    fun getNotFact(): NotFact {
        return notFacts.removeLast()
    }
}
