package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.generator.listener.block.BlockListener
import org.azauner.minicpp.ast.util.ScopeHandler
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class BlockStatListener(private val blockListener: BlockListener, private val scopeHandler: ScopeHandler) :
    minicppBaseListener() {

    private var blockStats = mutableListOf<org.azauner.minicpp.ast.node.BlockStat>()

    override fun enterBlockStat(ctx: minicppParser.BlockStatContext?) {
        scopeHandler.pushChildScope()
    }

    override fun exitBlockStat(ctx: minicppParser.BlockStatContext) {
        blockStats.add(org.azauner.minicpp.ast.node.BlockStat(block = blockListener.getBlock()))
        scopeHandler.popScope()
    }

    fun getBlockStat(): org.azauner.minicpp.ast.node.BlockStat {
        return blockStats.removeLast()
    }
}
