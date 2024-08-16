package org.azauner.ast.generator.visitor

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.ast.generator.visitor.field.ConstDefVisitor
import org.azauner.ast.generator.visitor.field.VarDefVisitor
import org.azauner.ast.generator.visitor.func.FuncDeclVisitor
import org.azauner.ast.generator.visitor.func.FuncDefVisitor
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
        return ctx.accept(FuncDeclVisitor())
    }

    override fun visitFuncDef(ctx: minicppParser.FuncDefContext): MiniCppEntry {
        return  ctx.accept(FuncDefVisitor())
    }

    override fun visitTerminal(node: TerminalNode): MiniCppEntry {
        return super.visitTerminal(node)
    }
}
