package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.node.Term
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class TermListener(private val notFactListener: NotFactListener, private val termEntryListener: TermEntryListener): minicppBaseListener() {

    private var terms = mutableListOf<Term>()

    override fun exitTerm(ctx: minicppParser.TermContext) {
        val entries = termEntryListener.getTermEntry(ctx.termEntry().size)
        terms.add(Term(notFactListener.getNotFact(), termEntries = entries))
    }

    fun getTerm(): Term {
        return terms.removeLast()
    }
}
