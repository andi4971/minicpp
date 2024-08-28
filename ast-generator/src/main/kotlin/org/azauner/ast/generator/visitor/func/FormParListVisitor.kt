package org.azauner.ast.generator.visitor.func

import org.azauner.ast.node.FormParList
import org.azauner.ast.node.FormParListEntries
import org.azauner.ast.node.VoidFormParListChild
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FormParListVisitor: minicppBaseVisitor<FormParList>() {
    override fun visitFormParList(ctx: minicppParser.FormParListContext): FormParList {
        if(ctx.VOID() != null) {
            return VoidFormParListChild
        }else {
            val entries = ctx.formParListEntry().map { entry ->
                entry.accept(FormParListEntryVisitor())
            }
            return FormParListEntries(entries)
        }
    }
}
