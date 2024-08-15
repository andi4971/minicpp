package org.azauner.ast.generator.visitor

import org.azauner.ast.node.*
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class InitVisitor : minicppBaseVisitor<Init>() {
    override fun visitInit(ctx: minicppParser.InitContext): Init {
        return ctx.initOption().accept(this)
    }

    override fun visitBooleanInit(ctx: minicppParser.BooleanInitContext): Init {
        return when (ctx.BOOLEAN().text) {
            "true" -> Init(BoolType(true))
            "false" -> Init(BoolType(false))
            else -> throw IllegalStateException("Unknown boolean init")
        }
    }

    override fun visitNullptrInit(ctx: minicppParser.NullptrInitContext): Init {
        return Init(NullPtrType)
    }

    override fun visitIntInit(ctx: minicppParser.IntInitContext): Init {
        val isPos = if (ctx.PLUSMINUS() != null) {
            ctx.PLUSMINUS().text == "+"
        } else {
            true
        }
        val value = ctx.INT().text.toIntOrNull() ?: throw IllegalStateException("Invalid int value")
        val signedValue = if (isPos) value else -value
        return Init(IntType(signedValue))
    }
}
