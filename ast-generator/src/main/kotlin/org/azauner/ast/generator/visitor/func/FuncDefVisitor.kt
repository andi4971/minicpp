package org.azauner.ast.generator.visitor.func
import org.azauner.ast.generator.visitor.block.BlockVisitor
import org.azauner.ast.node.FuncDef
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FuncDefVisitor: minicppBaseVisitor<FuncDef>() {
    override fun visitFuncDef(ctx: minicppParser.FuncDefContext): FuncDef {
        return FuncDef(
            funHead = ctx.funcHead().accept(FuncHeadVisitor()),
            block = ctx.block().accept(BlockVisitor())
        )
    }
}
