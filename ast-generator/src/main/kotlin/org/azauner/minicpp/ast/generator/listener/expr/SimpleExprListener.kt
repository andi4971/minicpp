package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.generator.listener.expr.term.TermListener
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class SimpleExprListener(
    private val termListener: TermListener,
    private val simpleExprEntryListener: SimpleExprEntryListener
) : minicppBaseListener() {

    private var simpleExprs = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.SimpleExpr>())

    override fun exitSimpleExpr(ctx: minicppParser.SimpleExprContext) {
        val sign = ctx.SIGN()?.let {
            if (it.text == "-") {
                org.azauner.minicpp.ast.node.Sign.MINUS
            } else {
                org.azauner.minicpp.ast.node.Sign.PLUS
            }
        }

        val entries = mutableListOf<org.azauner.minicpp.ast.node.SimpleExprEntry>()
        repeat(ctx.simpleExprEntry().size) {
            entries.add(simpleExprEntryListener.getSimpleExprEntry())
        }
        simpleExprs.add(
            org.azauner.minicpp.ast.node.SimpleExpr(
                sign = sign,
                term = termListener.getTerm(),
                simpleExprEntries = entries
            )
        )
    }

    fun getSimpleExpr(): org.azauner.minicpp.ast.node.SimpleExpr {
        return simpleExprs.removeLast()
    }
}
