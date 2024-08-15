package org.azauner.ast.generator.visitor.func
import org.azauner.ast.node.FuncDecl
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FuncDeclVisitor: minicppBaseVisitor<FuncDecl>() {
    override fun visitFuncDecl(ctx: minicppParser.FuncDeclContext): FuncDecl {
        return FuncDecl(ctx.funcHead().accept(FuncHeadVisitor()))
    }
}
