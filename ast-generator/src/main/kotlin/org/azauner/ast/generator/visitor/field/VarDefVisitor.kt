package org.azauner.ast.generator.visitor.field
import org.azauner.ast.generator.visitor.IdentVisitor
import org.azauner.ast.generator.visitor.TypeVisitor
import org.azauner.ast.node.VarDef
import org.azauner.ast.node.VarDefEntry
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class VarDefVisitor: minicppBaseVisitor<VarDef>() {
    override fun visitVarDef(ctx: minicppParser.VarDefContext): VarDef {
        val type = ctx.type().accept(TypeVisitor())
        val idents = ctx.varDefEntry().map { entry ->
            val ident = entry.IDENT().accept(IdentVisitor())
            val init = entry.init()?.accept(InitVisitor())
            VarDefEntry(ident, init)
        }
        return VarDef(type, idents)
    }
}
