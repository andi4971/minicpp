package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class TermListener(private val notFactListener: NotFactListener, private val termEntryListener: TermEntryListener): minicppBaseListener() {

    private var terms = mutableListOf<org.azauner.minicpp.ast.node.Term>()

    override fun exitTerm(ctx: minicppParser.TermContext) {
        val entries = mutableListOf<org.azauner.minicpp.ast.node.TermEntry>()
        repeat(ctx.termEntry().size) {
            entries.add(termEntryListener.getTermEntry())
        }
        terms.add(org.azauner.minicpp.ast.node.Term(notFactListener.getNotFact(), termEntries = entries.reversed()))
    }

    fun getTerm(): org.azauner.minicpp.ast.node.Term {
        return terms.removeLast()
    }
}
