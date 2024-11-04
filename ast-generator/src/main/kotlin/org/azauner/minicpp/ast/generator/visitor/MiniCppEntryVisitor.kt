package org.azauner.minicpp.ast.generator.visitor

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.minicpp.ast.generator.visitor.field.ConstDefVisitor
import org.azauner.minicpp.ast.generator.visitor.field.VarDefVisitor
import org.azauner.minicpp.ast.generator.visitor.func.FuncDeclVisitor
import org.azauner.minicpp.ast.generator.visitor.func.FuncDefVisitor
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class MiniCppEntryVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.MiniCppEntry>() {

    override fun visitConstDef(ctx: minicppParser.ConstDefContext): org.azauner.minicpp.ast.node.MiniCppEntry {
        return ctx.accept(ConstDefVisitor(scope))
    }

    override fun visitVarDef(ctx: minicppParser.VarDefContext): org.azauner.minicpp.ast.node.MiniCppEntry {
        return ctx.accept(VarDefVisitor(scope))
    }

    override fun visitFuncDecl(ctx: minicppParser.FuncDeclContext): org.azauner.minicpp.ast.node.MiniCppEntry {
        return ctx.accept(FuncDeclVisitor(scope))
    }

    override fun visitFuncDef(ctx: minicppParser.FuncDefContext): org.azauner.minicpp.ast.node.MiniCppEntry {
        return  ctx.accept(FuncDefVisitor(scope))
    }

    override fun visitTerminal(node: TerminalNode): org.azauner.minicpp.ast.node.MiniCppEntry {
        return org.azauner.minicpp.ast.node.Sem
    }
}
