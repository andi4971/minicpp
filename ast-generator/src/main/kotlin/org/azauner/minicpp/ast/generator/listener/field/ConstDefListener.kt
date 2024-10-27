package org.azauner.minicpp.ast.generator.listener.field

import org.azauner.minicpp.ast.node.ConstDef
import org.azauner.minicpp.ast.node.ConstDefEntry
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class ConstDefListener(
    private val typeListener: TypeListener,
    private val constDefEntryListener: ConstDefEntryListener
) : minicppBaseListener() {

    private val constDefs = mutableListOf<ConstDef>()

    override fun exitConstDef(ctx: minicppParser.ConstDefContext) {
        val type = typeListener.getType()
        //TODO
        val scope = Scope(null)
        val entries = constDefEntryListener.getAllConstDefEntries()
            .map {
                ConstDefEntry(
                    ident = it.ident,
                    value = it.value,
                    variable = scope.addVariable(it.ident, type, const = true, constValue = it.value.value.getValue())
                )
            }
        constDefs.add(ConstDef(
            type = type,
            entries = entries
        )        )
    }

    fun getConstDef(): ConstDef {
        return constDefs.removeLast()
    }
}
