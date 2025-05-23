package org.azauner.minicpp.ast.generator.listener.field

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

data class VarDefEntryData(
    val ident: org.azauner.minicpp.ast.node.Ident,
    val pointer: Boolean,
    val value: org.azauner.minicpp.ast.node.Init?
)

class VarDefEntryListener(private val initListener: InitListener) : minicppBaseListener() {

    private val varDefEntries = mutableListOf<VarDefEntryData>()

    override fun exitVarDefEntry(ctx: minicppParser.VarDefEntryContext) {
        val varDefEntry = VarDefEntryData(
            ident = org.azauner.minicpp.ast.node.Ident(ctx.IDENT().text),
            pointer = ctx.STAR() != null,
            value = ctx.init()?.let { initListener.getInit() }
        )
        varDefEntries.add(varDefEntry)
    }

    fun getVarDefEntries(): List<VarDefEntryData> {
        return varDefEntries.toList().also { varDefEntries.clear() }
    }
}
