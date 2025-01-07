package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.generator.listener.expr.term.TermListener
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class SimpleExprEntryListener(private val termListener: TermListener): minicppBaseListener() {

    private val simpleExprEntries =
        mutableListOf<org.azauner.minicpp.ast.node.SimpleExprEntry>()

    override fun exitSimpleExprEntry(ctx: minicppParser.SimpleExprEntryContext) {
        val sign  = ctx.SIGN().let {
            if (it.text == "-") {
                org.azauner.minicpp.ast.node.Sign.MINUS
            } else {
                org.azauner.minicpp.ast.node.Sign.PLUS
            }
        }

        simpleExprEntries.add(
            org.azauner.minicpp.ast.node.SimpleExprEntry(
                sign = sign,
                term = termListener.getTerm()
            )
        )
    }

    fun getSimpleExprEntry(): org.azauner.minicpp.ast.node.SimpleExprEntry {
        return simpleExprEntries.removeLast()
    }
}
