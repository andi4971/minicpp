package org.azauner.minicpp.ast.generator.listener.field

import org.azauner.minicpp.ast.node.BoolType
import org.azauner.minicpp.ast.node.Init
import org.azauner.minicpp.ast.node.IntType
import org.azauner.minicpp.ast.node.NullPtrType
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class InitListener : minicppBaseListener() {

    private val inits = mutableListOf<Init>()

    override fun exitIntInit(ctx: minicppParser.IntInitContext) {
        inits.add(Init(IntType(ctx.INT().text.toInt())))
    }

    override fun exitBooleanInit(ctx: minicppParser.BooleanInitContext) {
        inits.add(Init(BoolType(ctx.BOOLEAN().text.toBoolean())))
    }

    override fun exitNullptrInit(ctx: minicppParser.NullptrInitContext) {
        inits.add(Init(NullPtrType))
    }

    fun getInit(): Init {
        return inits.removeLast()
    }
}
