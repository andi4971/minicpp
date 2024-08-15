package org.azauner.ast.generator.visitor.func

import org.azauner.ast.generator.visitor.IdentVisitor
import org.azauner.ast.generator.visitor.TypeVisitor
import org.azauner.ast.node.FormParListEntry
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FormParListEntryVisitor: minicppBaseVisitor<FormParListEntry>() {

    override fun visitFormParListEntry(ctx: minicppParser.FormParListEntryContext): FormParListEntry {
        return FormParListEntry(
            type = ctx.type().accept(TypeVisitor()),
            ident = ctx.IDENT().accept(IdentVisitor()),
            pointer = ctx.STAR() != null,
            array = ctx.BRACKETS() != null
        )
    }
}
