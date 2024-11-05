package org.azauner.minicpp.ast.generator.listener.block

import org.azauner.minicpp.ast.util.ScopeHandler
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class BlockListener(private val blockEntryListener: BlockEntryListener,
                    private val scopeHandler: ScopeHandler
) : minicppBaseListener() {

    private val blocks = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.Block>())

    override fun enterBlock(ctx: minicppParser.BlockContext?) {
        scopeHandler.pushChildScope()
    }

    override fun exitBlock(ctx: minicppParser.BlockContext) {
        val scope = scopeHandler.getScope()
        val entries = mutableListOf<org.azauner.minicpp.ast.node.BlockEntry>()
        repeat(ctx.blockEntry().size) {
            entries.add(blockEntryListener.getBlockEntry())
        }
        blocks.add(org.azauner.minicpp.ast.node.Block(entries = entries.reversed(), scope = scope))
        scopeHandler.popScope()
    }

    fun getBlock(): org.azauner.minicpp.ast.node.Block {
        return blocks.removeLast()
    }
}
