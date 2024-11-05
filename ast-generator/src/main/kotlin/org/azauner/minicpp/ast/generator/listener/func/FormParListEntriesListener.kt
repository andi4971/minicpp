package org.azauner.minicpp.ast.generator.listener.func

import org.azauner.minicpp.ast.generator.listener.field.TypeListener
import org.azauner.minicpp.ast.util.toPointerTypeOptional
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class FormParListEntriesListener(private val typeListener: TypeListener) : minicppBaseListener() {

    private val formParListEntries =
        Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.FormParListEntry>())

    override fun exitFormParListEntry(ctx: minicppParser.FormParListEntryContext) {
        formParListEntries.add(
            org.azauner.minicpp.ast.node.FormParListEntry(
                type = typeListener.getType().toPointerTypeOptional(ctx.STAR() != null || ctx.BRACKETS() != null),
                ident = org.azauner.minicpp.ast.node.Ident(ctx.IDENT().text)
            )
        )
    }

    fun getFormParListEntries(): org.azauner.minicpp.ast.node.FormParListEntries {
        return org.azauner.minicpp.ast.node.FormParListEntries(
            entries = formParListEntries.toList()
        ).also {
            formParListEntries.clear()
        }
    }
}
