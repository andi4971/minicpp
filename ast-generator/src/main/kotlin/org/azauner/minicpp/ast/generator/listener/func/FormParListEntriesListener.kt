package org.azauner.minicpp.ast.generator.listener.func

import org.azauner.minicpp.ast.generator.listener.field.TypeListener
import org.azauner.minicpp.ast.node.FormParListEntries
import org.azauner.minicpp.ast.node.FormParListEntry
import org.azauner.minicpp.ast.node.Ident
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class FormParListEntriesListener(private val typeListener: TypeListener) : minicppBaseListener() {

    private val formParListEntries = mutableListOf<FormParListEntry>()

    override fun exitFormParListEntry(ctx: minicppParser.FormParListEntryContext) {
        formParListEntries.add(
            FormParListEntry(
                type = typeListener.getType(),
                ident = Ident(ctx.IDENT().text)
            )
        )
    }

    fun getFormParListEntries(): FormParListEntries {
        return FormParListEntries(
            entries = formParListEntries.toList()
        ).also {
            formParListEntries.clear()
        }
    }
}
