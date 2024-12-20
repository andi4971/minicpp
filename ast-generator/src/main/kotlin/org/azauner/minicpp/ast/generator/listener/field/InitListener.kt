package org.azauner.minicpp.ast.generator.listener.field

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class InitListener : minicppBaseListener() {

    private val inits = mutableListOf<org.azauner.minicpp.ast.node.Init>()

    override fun exitIntInit(ctx: minicppParser.IntInitContext) {
        var int = ctx.INT().text.toInt()
        if (ctx.SIGN() != null && ctx.SIGN().text == "-") {
            int = -int
        }
        inits.add(org.azauner.minicpp.ast.node.Init(org.azauner.minicpp.ast.node.IntType(int)))
    }

    override fun exitBooleanInit(ctx: minicppParser.BooleanInitContext) {
        inits.add(org.azauner.minicpp.ast.node.Init(org.azauner.minicpp.ast.node.BoolType(ctx.BOOLEAN().text.toBoolean())))
    }

    override fun exitNullptrInit(ctx: minicppParser.NullptrInitContext) {
        inits.add(org.azauner.minicpp.ast.node.Init(org.azauner.minicpp.ast.node.NullPtrType))
    }

    fun getInit(): org.azauner.minicpp.ast.node.Init {
        return inits.removeLast()
    }
}
