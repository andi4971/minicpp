package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class TermEntryListener(
    private val notFactListener: NotFactListener,
    private val termOperatorListener: TermOperatorListener
) : minicppBaseListener() {

    private var termEntries = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.TermEntry>())

    override fun exitTermEntry(ctx: minicppParser.TermEntryContext) {
        termEntries.add(
            org.azauner.minicpp.ast.node.TermEntry(
                notFact = notFactListener.getNotFact(),
                termOperator = termOperatorListener.getTermOperator()
            )
        )
    }

    fun getTermEntry(): org.azauner.minicpp.ast.node.TermEntry {
        return termEntries.removeLast()
    }
}
