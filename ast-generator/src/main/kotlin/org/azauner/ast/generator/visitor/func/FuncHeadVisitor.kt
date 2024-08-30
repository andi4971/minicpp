package org.azauner.ast.generator.visitor.func

import org.azauner.ast.generator.visitor.IdentVisitor
import org.azauner.ast.generator.visitor.TypeVisitor
import org.azauner.ast.node.FuncHead
import org.azauner.ast.util.toPointerTypeOptional
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FuncHeadVisitor : minicppBaseVisitor<FuncHead>() {
    override fun visitFuncHead(ctx: minicppParser.FuncHeadContext): FuncHead {
        return FuncHead(
            type = ctx.type().accept(TypeVisitor())
                .toPointerTypeOptional(ctx.STAR() != null),
            ident = ctx.IDENT().accept(IdentVisitor()),
            formParList = ctx.formParList()?.accept(FormParListVisitor())
        )
    }
}
