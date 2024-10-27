package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.node.TermEntry
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class TermEntryListener(
    private val notFactListener: NotFactListener,
    private val termOperatorListener: TermOperatorListener
) : minicppBaseListener() {

    private var termEntries = mutableListOf<TermEntry>()

    override fun exitTermEntry(ctx: minicppParser.TermEntryContext) {
        termEntries.add(
            TermEntry(
                notFact = notFactListener.getNotFact(),
                termOperator = termOperatorListener.getTermOperator()
            )
        )
    }

    fun getTermEntry(): TermEntry {
        return termEntries.removeLast()
    }

    fun getTermEntry(n: Int): List<TermEntry> {
        return termEntries.subList(termEntries.size - n, termEntries.size).also {
            termEntries.dropLast(n)
        }
    }
}
