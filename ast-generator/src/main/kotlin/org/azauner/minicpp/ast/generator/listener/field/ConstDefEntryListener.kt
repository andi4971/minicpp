package org.azauner.minicpp.ast.generator.listener.field

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

data class ConstDefEntryData(
    val ident: org.azauner.minicpp.ast.node.Ident,
    val value: org.azauner.minicpp.ast.node.Init
)

class ConstDefEntryListener(private val initListener: InitListener): minicppBaseListener() {

    private val constDefEntries = mutableListOf<ConstDefEntryData>()

    override fun exitConstDefEntry(ctx: minicppParser.ConstDefEntryContext) {
        constDefEntries.add(
            ConstDefEntryData(
                ident = org.azauner.minicpp.ast.node.Ident(ctx.IDENT().text),
                value = initListener.getInit(),
            )
        )
    }

    fun getConstDefEntry(): ConstDefEntryData {
        return constDefEntries.removeLast()
    }

    fun getAllConstDefEntries(): List<ConstDefEntryData> {
        return constDefEntries
    }
}
