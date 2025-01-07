package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class ExprEntryListener(
    private val orExprListener: OrExprListener,
    private val assignOperatorListener: AssignOperatorListener
) : minicppBaseListener() {

    private var exprEntries = mutableListOf<org.azauner.minicpp.ast.node.ExprEntry>()

    override fun exitExprEntry(ctx: minicppParser.ExprEntryContext) {
        exprEntries.add(
            org.azauner.minicpp.ast.node.ExprEntry(
                orExpr = orExprListener.getOrExpr(),
                assignOperator = assignOperatorListener.getAssignOperator()
            )
        )
    }

    fun getExprEntry(size: Int): List<org.azauner.minicpp.ast.node.ExprEntry> {
        val entries = mutableListOf<org.azauner.minicpp.ast.node.ExprEntry>()
        repeat(size) {
            entries.add(exprEntries.removeLast())
        }
        return entries.reversed()
    }
}
