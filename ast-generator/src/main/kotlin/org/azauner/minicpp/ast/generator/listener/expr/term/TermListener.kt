package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.node.Term
import org.azauner.minicpp.ast.node.TermEntry
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class TermListener(private val notFactListener: NotFactListener, private val termEntryListener: TermEntryListener): minicppBaseListener() {

    private var terms = Collections.synchronizedList(mutableListOf<Term>())

    override fun exitTerm(ctx: minicppParser.TermContext) {
        val entries = mutableListOf<TermEntry>()
        repeat(ctx.termEntry().size) {
            entries.add(termEntryListener.getTermEntry())
        }
        terms.add(Term(notFactListener.getNotFact(), termEntries = entries))
    }

    fun getTerm(): Term {
        return terms.removeLast()
    }
}
