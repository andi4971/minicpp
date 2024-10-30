package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.node.Stat
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class StatListener(
) : minicppBaseListener() {

    fun initListeners(
        breakStatListener: BreakStatListener,
        inputStatListener: InputStatListener,
        deleteStatListener: DeleteStatListener,
        emptyStatListener: EmptyStatListener,
        blockStatListener: BlockStatListener,
        returnStatListener: ReturnStatListener,
        exprStatListener: ExprStatListener,
        whileStatListener: WhileStatListener,
        ifStatListener: IfStatListener,
        outputStatListener: OutputStatListener
    ) {
        this.breakStatListener = breakStatListener
        this.inputStatListener = inputStatListener
        this.deleteStatListener = deleteStatListener
        this.emptyStatListener = emptyStatListener
        this.blockStatListener = blockStatListener
        this.returnStatListener = returnStatListener
        this.exprStatListener = exprStatListener
        this.whileStatListener = whileStatListener
        this.ifStatListener = ifStatListener
        this.outputStatListener = outputStatListener
    }

    private lateinit var breakStatListener: BreakStatListener
    private lateinit var inputStatListener: InputStatListener
    private lateinit var deleteStatListener: DeleteStatListener
    private lateinit var emptyStatListener: EmptyStatListener
    private lateinit var blockStatListener: BlockStatListener
    private lateinit var returnStatListener: ReturnStatListener
    private lateinit var exprStatListener: ExprStatListener
    private lateinit var whileStatListener: WhileStatListener
    private lateinit var ifStatListener: IfStatListener
    private lateinit var outputStatListener: OutputStatListener

    private val stats = Collections.synchronizedList(mutableListOf<Stat>())

    override fun exitStat(ctx: minicppParser.StatContext) {
        val stat = when {
            ctx.ifStat() != null -> ifStatListener.getIfStat()
            ctx.whileStat() != null -> whileStatListener.getWhileStat()
            ctx.blockStat() != null -> blockStatListener.getBlockStat()
            ctx.returnStat() != null -> returnStatListener.getReturnStat()
            ctx.exprStat() != null -> exprStatListener.getExprStat()
            ctx.breakStat() != null -> breakStatListener.getBreakStat()
            ctx.deleteStat() != null -> deleteStatListener.getDeleteStat()
            ctx.emptyStat() != null -> emptyStatListener.getEmptyStat()
            ctx.inputStat() != null -> inputStatListener.getInputStat()
            ctx.outputStat() != null -> outputStatListener.getOutputStat()
            else -> throw IllegalStateException("Unknown stat")
        }
        stats.add(stat)
    }

    fun getStat(): Stat {
        return stats.removeLast()
    }
}
