package org.azauner.minicpp.ast.generator.listener.block

import org.azauner.minicpp.ast.generator.listener.field.ConstDefListener
import org.azauner.minicpp.ast.generator.listener.field.VarDefListener
import org.azauner.minicpp.ast.generator.listener.stat.StatListener
import org.azauner.minicpp.ast.node.BlockEntry
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class BlockEntryListener(
    private val statListener: StatListener,
    private val varDefListener: VarDefListener,
    private val constDefListener: ConstDefListener
) : minicppBaseListener() {

    private val blockEntries = Collections.synchronizedList(mutableListOf<BlockEntry>())


    override fun exitBlockEntry(ctx: minicppParser.BlockEntryContext) {
        val entry = when {
            ctx.stat() != null -> statListener.getStat()
            ctx.varDef() != null -> varDefListener.getVarDef()
            ctx.constDef() != null -> constDefListener.getConstDef()
            else -> throw IllegalStateException("Unknown block entry")
        }
        blockEntries.add(entry)
    }

    fun getBlockEntry(): BlockEntry {
        return blockEntries.removeLast()
    }
}
