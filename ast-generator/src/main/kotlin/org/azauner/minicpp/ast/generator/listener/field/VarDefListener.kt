package org.azauner.minicpp.ast.generator.listener.field

import org.azauner.minicpp.ast.generator.listener.ScopeHandler
import org.azauner.minicpp.ast.util.toPointerTypeOptional
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class VarDefListener(private val typeListener: TypeListener, private val varDefEntryListener: VarDefEntryListener,
    private val scopeHandler: ScopeHandler) :
    minicppBaseListener() {

    private val varDefs = mutableListOf<org.azauner.minicpp.ast.node.VarDef>()

    override fun exitVarDef(ctx: minicppParser.VarDefContext) {
        val type = typeListener.getType()
        val scope = scopeHandler.getScope()
        val entries = varDefEntryListener.getVarDefEntries()
            .map {
                org.azauner.minicpp.ast.node.VarDefEntry(
                    ident = it.ident,
                    pointer = it.pointer,
                    value = it.value,
                    variable = scope.addVariable(
                        it.ident,
                        type.toPointerTypeOptional(it.pointer),
                        const = false,
                        constValue = it.value?.value?.getValue()
                    )
                )
            }

        varDefs.add(org.azauner.minicpp.ast.node.VarDef(type, entries))
    }

    fun getVarDef(): org.azauner.minicpp.ast.node.VarDef {
        return varDefs.removeLast()
    }
}
