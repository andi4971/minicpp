package org.azauner.minicpp.ast.generator.visitor.func

import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FormParListVisitor : minicppBaseVisitor<org.azauner.minicpp.ast.node.FormParList>() {
    override fun visitFormParList(ctx: minicppParser.FormParListContext): org.azauner.minicpp.ast.node.FormParList {
        if(ctx.VOID() != null) {
            return org.azauner.minicpp.ast.node.VoidFormParListChild
        }else {
            val entries = ctx.formParListEntry().map { entry ->
                entry.accept(FormParListEntryVisitor())
            }
            return org.azauner.minicpp.ast.node.FormParListEntries(entries)
        }
    }
}
