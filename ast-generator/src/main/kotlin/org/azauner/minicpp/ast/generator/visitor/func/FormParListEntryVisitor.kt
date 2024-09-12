package org.azauner.minicpp.ast.generator.visitor.func

import org.azauner.minicpp.ast.generator.visitor.IdentVisitor
import org.azauner.minicpp.ast.generator.visitor.TypeVisitor
import org.azauner.minicpp.ast.node.FormParListEntry
import org.azauner.minicpp.ast.util.toPointerTypeOptional
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