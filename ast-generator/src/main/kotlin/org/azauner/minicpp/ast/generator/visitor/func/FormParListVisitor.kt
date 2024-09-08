package org.azauner.minicpp.ast.generator.visitor.func

import org.azauner.minicpp.ast.node.FormParList
import org.azauner.minicpp.ast.node.FormParListEntries
import org.azauner.minicpp.ast.node.VoidFormParListChild
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
