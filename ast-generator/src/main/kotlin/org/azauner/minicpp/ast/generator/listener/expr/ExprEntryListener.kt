package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.node.ExprEntry
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class ExprEntryListener(
    private val orExprListener: OrExprListener,
    private val assignOperatorListener: AssignOperatorListener
) : minicppBaseListener() {

    private var exprEntries = mutableListOf<ExprEntry>()

    override fun exitExprEntry(ctx: minicppParser.ExprEntryContext) {

        exprEntries.add(ExprEntry(orExpr = orExprListener.getOrExpr(), assignOperator = assignOperatorListener.getAssignOperator()))
    }

    fun getExprEntry(size: Int): List<ExprEntry> {
        return exprEntries.subList(exprEntries.size - size, exprEntries.size)
    }
}
