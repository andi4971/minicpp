package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.node.ExprEntry
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class ExprEntryListener(
    private val orExprListener: OrExprListener,
    private val assignOperatorListener: AssignOperatorListener
) : minicppBaseListener() {

    private var exprEntries = Collections.synchronizedList(mutableListOf<ExprEntry>())

    override fun exitExprEntry(ctx: minicppParser.ExprEntryContext) {
        exprEntries.add(ExprEntry(orExpr = orExprListener.getOrExpr(), assignOperator = assignOperatorListener.getAssignOperator()))
    }

    fun getExprEntry(size: Int): List<ExprEntry> {
        val entries = mutableListOf<ExprEntry>()
        repeat(size) {
            entries.add(exprEntries.removeLast())
        }
        return entries
    }
}
