package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.generator.listener.block.BlockListener
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class BlockStatListener(private val blockListener: BlockListener): minicppBaseListener() {

    private var blockStats = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.BlockStat>())

    override fun exitBlockStat(ctx: minicppParser.BlockStatContext) {
        blockStats.add(org.azauner.minicpp.ast.node.BlockStat(block = blockListener.getBlock()))
    }

    fun getBlockStat(): org.azauner.minicpp.ast.node.BlockStat {
        return blockStats.removeLast()
    }
}
