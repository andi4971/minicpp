package org.azauner.minicpp.ast.generator.listener.field

import org.azauner.minicpp.ast.node.ExprType
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class TypeListener : minicppBaseListener() {

    private val types = mutableListOf<ExprType>()

    override fun exitIntType(ctx: minicppParser.IntTypeContext) {
        types.add(ExprType.INT)
    }

    override fun exitBoolType(ctx: minicppParser.BoolTypeContext) {
        types.add(ExprType.BOOL)
    }

    override fun exitVoidType(ctx: minicppParser.VoidTypeContext) {
        types.add(ExprType.VOID)
    }

    fun getType(): ExprType {
        return types.removeLast()
    }
}
