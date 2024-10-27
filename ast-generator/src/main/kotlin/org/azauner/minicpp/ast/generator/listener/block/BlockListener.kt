package org.azauner.minicpp.ast.generator.listener.block

import org.azauner.minicpp.ast.node.Block
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class BlockListener(private val blockEntryListener: BlockEntryListener) : minicppBaseListener() {

    private val blocks = mutableListOf<Block>()

    override fun exitBlock(ctx: minicppParser.BlockContext) {
        //Todo
        val scope = Scope(null)
        blocks.add(Block(entries = blockEntryListener.getBlockEntries(), scope = scope))
    }

    fun getBlock(): Block {
        return blocks.removeLast()
    }
}
