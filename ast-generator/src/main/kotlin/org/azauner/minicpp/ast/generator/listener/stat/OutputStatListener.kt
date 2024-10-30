package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.minicpp.ast.node.Endl
import org.azauner.minicpp.ast.node.OutputStat
import org.azauner.minicpp.ast.node.OutputStatEntry
import org.azauner.minicpp.ast.node.Text
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class OutputStatListener(private val exprListener: ExprListener): minicppBaseListener() {

    private var outputStats = Collections.synchronizedList(mutableListOf<OutputStat>())

    private val outputStatEntries = Collections.synchronizedList(mutableListOf<OutputStatEntry>())

    override fun exitExprOutputStatEntry(ctx: minicppParser.ExprOutputStatEntryContext?) {
        outputStatEntries.add(exprListener.getExpr())
    }

    override fun exitStringOutputStatEntry(ctx: minicppParser.StringOutputStatEntryContext?) {
        outputStatEntries.add(Text(ctx!!.STRING().text))
    }

    override fun exitEndlOutputStatEntry(ctx: minicppParser.EndlOutputStatEntryContext?) {
        outputStatEntries.add(Endl)
    }

    override fun exitOutputStat(ctx: minicppParser.OutputStatContext) {
        val entries = mutableListOf<OutputStatEntry>()
        repeat(ctx.outputStatEntry().size) {
            entries.add(outputStatEntries.removeLast())
        }
        outputStats.add(OutputStat(entries))
    }



    fun getOutputStat(): OutputStat {
        return outputStats.removeLast()
    }
}
