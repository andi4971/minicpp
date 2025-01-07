package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class OutputStatListener(private val exprListener: ExprListener): minicppBaseListener() {

    private var outputStats = mutableListOf<org.azauner.minicpp.ast.node.OutputStat>()

    private val outputStatEntries =
        mutableListOf<org.azauner.minicpp.ast.node.OutputStatEntry>()

    override fun exitExprOutputStatEntry(ctx: minicppParser.ExprOutputStatEntryContext?) {
        outputStatEntries.add(exprListener.getExpr())
    }

    override fun exitStringOutputStatEntry(ctx: minicppParser.StringOutputStatEntryContext?) {
        outputStatEntries.add(
            org.azauner.minicpp.ast.node.Text(
                ctx!!.STRING().text
                    .trim('"')
                    .replace("\\\\", "\\")
                    .replace("\\t", "\t")
                    .replace("\\n", "\n")
                    .replace("\\r", "\r")
                    .replace("\\\"", "\"")
            )
        )
    }

    override fun exitEndlOutputStatEntry(ctx: minicppParser.EndlOutputStatEntryContext?) {
        outputStatEntries.add(org.azauner.minicpp.ast.node.Endl)
    }

    override fun exitOutputStat(ctx: minicppParser.OutputStatContext) {
        val entries = mutableListOf<org.azauner.minicpp.ast.node.OutputStatEntry>()
        repeat(ctx.outputStatEntry().size) {
            entries.add(outputStatEntries.removeLast())
        }
        outputStats.add(org.azauner.minicpp.ast.node.OutputStat(entries.reversed()))
    }


    fun getOutputStat(): org.azauner.minicpp.ast.node.OutputStat {
        return outputStats.removeLast()
    }
}
