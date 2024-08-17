package org.azauner.ast.generator.visitor.field

import org.azauner.ast.node.*
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class InitVisitor : minicppBaseVisitor<Init>() {
    override fun visitInit(ctx: minicppParser.InitContext): Init {
        return ctx.initOption().accept(this)
    }

    override fun visitBooleanInit(ctx: minicppParser.BooleanInitContext): Init {
        return Init(ctx.BOOLEAN().accept(BooleanVisitor()))
    }

    override fun visitNullptrInit(ctx: minicppParser.NullptrInitContext): Init {
        return Init(NullPtrType)
    }

    override fun visitIntInit(ctx: minicppParser.IntInitContext): Init {
        val isPos = if (ctx.SIGN() != null) {
            ctx.SIGN().text == "+"
        } else {
            true
        }
        return Init(ctx.INT().accept(IntVisitor(isPos)))
    }
}
