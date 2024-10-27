package org.azauner.minicpp.ast.generator.listener.block

import org.azauner.minicpp.ast.node.BlockEntry
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class BlockEntryListener(private val statListener: StatListener): minicppBaseListener() {

    private val blockEntries = mutableListOf<BlockEntry>()

    override fun exitBlockEntry(ctx: minicppParser.BlockEntryContext) {
        blockEntries.add(BlockEntry())
    }

   fun getBlockEntries(): List<BlockEntry> {
        return blockEntries.toList()
        .also {
            blockEntries.clear()
        }
    }
}
