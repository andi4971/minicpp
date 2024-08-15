package org.azauner.ast.generator.visitor

import org.azauner.ast.node.ConstDef
import org.azauner.ast.node.ConstDefEntry
import org.azauner.ast.node.Ident
import org.azauner.ast.node.MiniCppEntry
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class MiniCppEntryVisitor: minicppBaseVisitor<MiniCppEntry>() {

    override fun visitConstDef(ctx: minicppParser.ConstDefContext): MiniCppEntry {
        return ctx.accept(ConstDefVisitor())
    }

    override fun visitVarDef(ctx: minicppParser.VarDefContext): MiniCppEntry {
        return ctx.accept(VarDefVisitor())
    }

    override fun visitFuncDecl(ctx: minicppParser.FuncDeclContext): MiniCppEntry {
        return
    }

    override fun visitFuncDef(ctx: minicppParser.FuncDefContext): MiniCppEntry {
        return
    }

    override fun visitEmptyStat(ctx: minicppParser.EmptyStatContext): MiniCppEntry {
        return
    }
}
