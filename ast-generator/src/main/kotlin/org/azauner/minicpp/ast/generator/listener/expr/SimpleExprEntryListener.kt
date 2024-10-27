package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.generator.listener.expr.term.TermListener
import org.azauner.minicpp.ast.node.Sign
import org.azauner.minicpp.ast.node.SimpleExprEntry
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class SimpleExprEntryListener(private val termListener: TermListener): minicppBaseListener() {

    private val simpleExprEntries = mutableListOf<SimpleExprEntry>()

    override fun exitSimpleExprEntry(ctx: minicppParser.SimpleExprEntryContext) {
        val sign  = ctx.SIGN().let {
            if (it.text == "-") {
                Sign.MINUS
            } else {
                Sign.PLUS
            }
        }

        simpleExprEntries.add(SimpleExprEntry(
            sign = sign,
            term = termListener.getTerm()
        ))
    }

    fun getSimpleExprEntry(): SimpleExprEntry {
        return simpleExprEntries.removeLast()
    }
    fun getSimpleExprEntry(n: Int): List<SimpleExprEntry> {
        return simpleExprEntries.subList(simpleExprEntries.size - n, simpleExprEntries.size).also {
            simpleExprEntries.dropLast(n)
        }
    }
}
