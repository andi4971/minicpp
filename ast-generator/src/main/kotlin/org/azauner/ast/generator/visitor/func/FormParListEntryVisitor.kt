package org.azauner.ast.generator.visitor.func

import org.azauner.ast.generator.visitor.IdentVisitor
import org.azauner.ast.generator.visitor.TypeVisitor
import org.azauner.ast.node.FormParListEntry
import org.azauner.ast.util.toPointerTypeOptional
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FormParListEntryVisitor: minicppBaseVisitor<FormParListEntry>() {

    override fun visitFormParListEntry(ctx: minicppParser.FormParListEntryContext): FormParListEntry {
        return FormParListEntry(
            //todo figure out what the use of the brackets are
            type = ctx.type().accept(TypeVisitor()).toPointerTypeOptional(ctx.STAR() != null),
            ident = ctx.IDENT().accept(IdentVisitor()),
        )
    }
}
