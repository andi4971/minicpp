package org.azauner.minicpp.ast.generator.visitor

import org.azauner.minicpp.ast.node.ExprType.*
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class TypeVisitor : minicppBaseVisitor<org.azauner.minicpp.ast.node.ExprType>() {

    override fun visitIntType(ctx: minicppParser.IntTypeContext): org.azauner.minicpp.ast.node.ExprType {
        return INT
    }

    override fun visitBoolType(ctx: minicppParser.BoolTypeContext): org.azauner.minicpp.ast.node.ExprType {
        return BOOL
    }

    override fun visitVoidType(ctx: minicppParser.VoidTypeContext): org.azauner.minicpp.ast.node.ExprType {
        return VOID
    }
}
