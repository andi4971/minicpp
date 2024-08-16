package org.azauner.ast.generator.visitor.field
import org.azauner.ast.generator.visitor.IdentVisitor
import org.azauner.ast.generator.visitor.TypeVisitor
import org.azauner.ast.node.ConstDef
import org.azauner.ast.node.ConstDefEntry
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser
class ConstDefVisitor : minicppBaseVisitor<ConstDef>() {

    override fun visitConstDef(ctx: minicppParser.ConstDefContext): ConstDef {
        val type = ctx.type().accept(TypeVisitor())
        val idents = ctx.constDefEntry().map { entry ->
            val ident = entry.IDENT().accept(IdentVisitor())
            val init = entry.init().accept(InitVisitor())
            ConstDefEntry(ident, init)
        }
        return ConstDef(type, idents)
    }
}