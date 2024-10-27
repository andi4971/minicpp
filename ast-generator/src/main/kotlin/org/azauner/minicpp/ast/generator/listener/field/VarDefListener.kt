package org.azauner.minicpp.ast.generator.listener.field

import org.azauner.minicpp.ast.node.VarDef
import org.azauner.minicpp.ast.node.VarDefEntry
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class VarDefListener(private val typeListener: TypeListener, private val varDefEntryListener: VarDefEntryListener) :
    minicppBaseListener() {

    private val varDefs = mutableListOf<VarDef>()

    override fun exitVarDef(ctx: minicppParser.VarDefContext) {
        val type = typeListener.getType()
        //TODO
        val scope = Scope(null)
        val entries = varDefEntryListener.getVarDefEntries()
            .map {
                VarDefEntry(
                    ident = it.ident,
                    pointer = it.pointer,
                    value = it.value,
                    variable = scope.addVariable(
                        it.ident,
                        type,
                        const = false,
                        constValue = it.value?.value?.getValue()
                    )
                )
            }

        varDefs.add(VarDef(type, entries))
    }

    fun getVarDef(): VarDef {
        return varDefs.removeLast()
    }
}
