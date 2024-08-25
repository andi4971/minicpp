package org.azauner.ast.generator.visitor

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.ast.generator.visitor.field.ConstDefVisitor
import org.azauner.ast.generator.visitor.field.VarDefVisitor
import org.azauner.ast.generator.visitor.func.FuncDeclVisitor
import org.azauner.ast.generator.visitor.func.FuncDefVisitor
import org.azauner.ast.node.MiniCppEntry
import org.azauner.ast.node.Sem
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class MiniCppEntryVisitor(private val scope: Scope) : minicppBaseVisitor<MiniCppEntry>() {

    override fun visitConstDef(ctx: minicppParser.ConstDefContext): MiniCppEntry {
        return ctx.accept(ConstDefVisitor(scope))
    }

    override fun visitVarDef(ctx: minicppParser.VarDefContext): MiniCppEntry {
        return ctx.accept(VarDefVisitor(scope))
    }

    override fun visitFuncDecl(ctx: minicppParser.FuncDeclContext): MiniCppEntry {
        return ctx.accept(FuncDeclVisitor(scope))
    }

    override fun visitFuncDef(ctx: minicppParser.FuncDefContext): MiniCppEntry {
        return  ctx.accept(FuncDefVisitor(scope))
    }

    override fun visitTerminal(node: TerminalNode): MiniCppEntry {
        return Sem
    }
}
