package org.azauner.minicpp.ast.generator.listener.field

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class TypeListener : minicppBaseListener() {

    private val types = mutableListOf<org.azauner.minicpp.ast.node.ExprType>()

    override fun exitIntType(ctx: minicppParser.IntTypeContext) {
        types.add(org.azauner.minicpp.ast.node.ExprType.INT)
    }

    override fun exitBoolType(ctx: minicppParser.BoolTypeContext) {
        types.add(org.azauner.minicpp.ast.node.ExprType.BOOL)
    }

    override fun exitVoidType(ctx: minicppParser.VoidTypeContext) {
        types.add(org.azauner.minicpp.ast.node.ExprType.VOID)
    }

    fun getType(): org.azauner.minicpp.ast.node.ExprType {
        return types.removeLast()
    }
}
