package org.azauner.minicpp.ast.generator.listener.field

import org.azauner.minicpp.ast.generator.listener.ScopeHandler
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class ConstDefListener(
    private val typeListener: TypeListener,
    private val constDefEntryListener: ConstDefEntryListener,
    private val scopeHandler: ScopeHandler
) : minicppBaseListener() {

    private val constDefs = mutableListOf<org.azauner.minicpp.ast.node.ConstDef>()

    override fun exitConstDef(ctx: minicppParser.ConstDefContext) {
        val type = typeListener.getType()
        val scope = scopeHandler.getScope()
        val entries = constDefEntryListener.getAllConstDefEntries()
            .map {
                org.azauner.minicpp.ast.node.ConstDefEntry(
                    ident = it.ident,
                    value = it.value,
                    variable = scope.addVariable(it.ident, type, const = true, constValue = it.value.value.getValue())
                )
            }
        constDefs.add(
            org.azauner.minicpp.ast.node.ConstDef(
                type = type,
                entries = entries
            )
        )
    }

    fun getConstDef(): org.azauner.minicpp.ast.node.ConstDef {
        return constDefs.removeLast()
    }
}
