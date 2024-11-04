package org.azauner.minicpp.ast.generator.visitor.func

import org.azauner.minicpp.ast.generator.visitor.IdentVisitor
import org.azauner.minicpp.ast.generator.visitor.TypeVisitor
import org.azauner.minicpp.ast.util.toPointerTypeOptional
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FormParListEntryVisitor : minicppBaseVisitor<org.azauner.minicpp.ast.node.FormParListEntry>() {

    override fun visitFormParListEntry(ctx: minicppParser.FormParListEntryContext): org.azauner.minicpp.ast.node.FormParListEntry {
        return org.azauner.minicpp.ast.node.FormParListEntry(
            type = ctx.type().accept(TypeVisitor()).toPointerTypeOptional(ctx.STAR() != null || ctx.BRACKETS() != null),
            ident = ctx.IDENT().accept(IdentVisitor()),
        )
    }
}
