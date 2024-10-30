package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.generator.listener.expr.term.TermListener
import org.azauner.minicpp.ast.node.Sign
import org.azauner.minicpp.ast.node.SimpleExpr
import org.azauner.minicpp.ast.node.SimpleExprEntry
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class SimpleExprListener(
    private val termListener: TermListener,
    private val simpleExprEntryListener: SimpleExprEntryListener
) : minicppBaseListener() {

    private var simpleExprs = Collections.synchronizedList(mutableListOf<SimpleExpr>())

    override fun exitSimpleExpr(ctx: minicppParser.SimpleExprContext) {
        val sign = ctx.SIGN()?.let {
            if (it.text == "-") {
                Sign.MINUS
            } else {
                Sign.PLUS
            }
        }

        val entries = mutableListOf<SimpleExprEntry>()
        repeat(ctx.simpleExprEntry().size) {
            entries.add(simpleExprEntryListener.getSimpleExprEntry())
        }
        simpleExprs.add(
            SimpleExpr(
                sign = sign,
                term = termListener.getTerm(),
                simpleExprEntries = entries
            )
        )
    }

    fun getSimpleExpr(): SimpleExpr {
        return simpleExprs.removeLast()
    }
}
