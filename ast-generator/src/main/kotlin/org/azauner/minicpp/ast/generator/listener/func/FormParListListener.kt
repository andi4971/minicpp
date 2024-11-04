package org.azauner.minicpp.ast.generator.listener.func

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class FormParListListener(private val formParListEntriesListener: FormParListEntriesListener): minicppBaseListener() {

    private val formParLists = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.FormParList>())

    override fun exitFormParList(ctx: minicppParser.FormParListContext) {
        if(ctx.VOID() != null) {
            formParLists.add(org.azauner.minicpp.ast.node.VoidFormParListChild)
        }else {
            formParLists.add(formParListEntriesListener.getFormParListEntries())
        }
    }

    fun getFormParList(): org.azauner.minicpp.ast.node.FormParList {
        return formParLists.removeLast()
    }
}
