package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.node.Stat
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class StatListener(private val breakStatListener: BreakStatListener,
    private val inputStatListener: InputStatListener,
    private val deleteStatListener: DeleteStatListener,
    private val emptyStatListener: EmptyStatListener,
    private val blockStatListener: BlockStatListener,
): minicppBaseListener() {

    private val stats = mutableListOf<Stat>()

    override fun exitStat(ctx: minicppParser.StatContext) {
        val stat = when {
            ctx.ifStat() != null -> ifStatListener.getIfStat(),
            ctx.whileStat() != null -> whileStatListener.getWhileStat(),
            ctx.blockStat() != null -> blockStatListener.getBlockStat(),
            ctx.returnStat() != null -> returnStatListener.getReturnStat(),
            ctx.exprStat() != null -> exprStatListener.getExprStat(),
            ctx.breakStat() != null -> breakStatListener.getBreakStat(),
            ctx.deleteStat() != null -> deleteStatListener.getDeleteStat(),
            ctx.emptyStat() != null -> emptyStatListener.getEmptyStat(),
            ctx.inputStat() != null -> inputStatListener.getInputStat(),
            ctx.outputStat() != null -> outputStatListener.getOutputStat()
        }
        stats.add(stat)
    }

    fun getStats(): List<Stat> {
        return stats.toList()
        .also {
            stats.clear()
        }
    }
}
