package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.generator.listener.block.BlockListener
import org.azauner.minicpp.ast.node.BlockStat
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class BlockStatListener(private val blockListener: BlockListener): minicppBaseListener() {

    private var blockStats = mutableListOf<BlockStat>()

    override fun exitBlockStat(ctx: minicppParser.BlockStatContext) {
        blockStats.add(BlockStat(block = blockListener.getBlock()))
    }

    fun getBlockStat(): BlockStat {
        return blockStats.removeLast()
    }
}
