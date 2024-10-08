package org.azauner.minicpp.ast.generator.visitor
import org.azauner.minicpp.ast.node.ExprType
import org.azauner.minicpp.ast.node.ExprType.*

import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class TypeVisitor: minicppBaseVisitor<ExprType>() {

    override fun visitIntType(ctx: minicppParser.IntTypeContext): ExprType {
        return INT
    }

    override fun visitBoolType(ctx: minicppParser.BoolTypeContext): ExprType {
        return BOOL
    }

    override fun visitVoidType(ctx: minicppParser.VoidTypeContext): ExprType {
        return VOID
    }
}
