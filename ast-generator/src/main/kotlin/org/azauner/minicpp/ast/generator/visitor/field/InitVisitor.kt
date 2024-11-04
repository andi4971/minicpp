package org.azauner.minicpp.ast.generator.visitor.field

import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class InitVisitor : minicppBaseVisitor<org.azauner.minicpp.ast.node.Init>() {
    override fun visitInit(ctx: minicppParser.InitContext): org.azauner.minicpp.ast.node.Init {
        return ctx.initOption().accept(this)
    }

    override fun visitBooleanInit(ctx: minicppParser.BooleanInitContext): org.azauner.minicpp.ast.node.Init {
        return org.azauner.minicpp.ast.node.Init(ctx.BOOLEAN().accept(BooleanVisitor()))
    }

    override fun visitNullptrInit(ctx: minicppParser.NullptrInitContext): org.azauner.minicpp.ast.node.Init {
        return org.azauner.minicpp.ast.node.Init(org.azauner.minicpp.ast.node.NullPtrType)
    }

    override fun visitIntInit(ctx: minicppParser.IntInitContext): org.azauner.minicpp.ast.node.Init {
        val isPos = if (ctx.SIGN() != null) {
            ctx.SIGN().text == "+"
        } else {
            true
        }
        return org.azauner.minicpp.ast.node.Init(ctx.INT().accept(IntVisitor(isPos)))
    }
}
