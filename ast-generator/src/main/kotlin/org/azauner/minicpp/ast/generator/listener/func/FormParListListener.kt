package org.azauner.minicpp.ast.generator.listener.func

import org.azauner.minicpp.ast.node.FormParList
import org.azauner.minicpp.ast.node.VoidFormParListChild
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class FormParListListener(private val formParListEntriesListener: FormParListEntriesListener): minicppBaseListener() {

    private val formParLists = mutableListOf<FormParList>()

    override fun exitFormParList(ctx: minicppParser.FormParListContext) {
        if(ctx.VOID() != null) {
            formParLists.add(VoidFormParListChild)
        }else {
            formParLists.add(formParListEntriesListener.getFormParListEntries())
        }
    }

    fun getFormParList(): FormParList {
        return formParLists.removeLast()
    }
}
