package org.azauner.minicpp.ast.generator.listener.block

import org.azauner.minicpp.ast.node.Block
import org.azauner.minicpp.ast.node.BlockEntry
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class BlockListener(private val blockEntryListener: BlockEntryListener) : minicppBaseListener() {

    private val blocks = Collections.synchronizedList(mutableListOf<Block>())

    override fun exitBlock(ctx: minicppParser.BlockContext) {
        //Todo
        val scope = Scope(null)
        val entries = mutableListOf<BlockEntry>()
        repeat(ctx.blockEntry().size) {
            entries.add(blockEntryListener.getBlockEntry())
        }
        blocks.add(Block(entries = entries.reversed(), scope = scope))
    }

    fun getBlock(): Block {
        return blocks.removeLast()
    }
}
