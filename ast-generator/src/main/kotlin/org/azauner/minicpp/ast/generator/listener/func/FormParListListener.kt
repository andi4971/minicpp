package org.azauner.minicpp.ast.generator.listener.func

import org.azauner.minicpp.ast.util.ScopeHandler
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class FormParListListener(
    private val formParListEntriesListener: FormParListEntriesListener,
    private val scopeHandler: ScopeHandler,
    private val funcDefListener: FuncDefListener
) : minicppBaseListener() {

    private val formParLists = mutableListOf<org.azauner.minicpp.ast.node.FormParList>()

    override fun exitFormParList(ctx: minicppParser.FormParListContext) {
        if(ctx.VOID() != null) {
            formParLists.add(org.azauner.minicpp.ast.node.VoidFormParListChild)
        }else {
            val entries = formParListEntriesListener.getFormParListEntries()
            formParLists.add(entries)
            if (funcDefListener.addNextFormParListToVariables) {
                entries.entries.forEach { entry ->
                    scopeHandler.getScope().addVariable(
                        ident = entry.ident,
                        type = entry.type
                    )
                }
            }
        }
        funcDefListener.addNextFormParListToVariables = false
    }

    fun getFormParList(): org.azauner.minicpp.ast.node.FormParList {
        return formParLists.removeLast()
    }
}
