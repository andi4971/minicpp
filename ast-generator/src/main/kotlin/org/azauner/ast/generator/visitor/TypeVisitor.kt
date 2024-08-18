package org.azauner.ast.generator.visitor
import org.azauner.ast.node.Type
import org.azauner.ast.node.Type.*
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class TypeVisitor: minicppBaseVisitor<Type>() {

    override fun visitIntType(ctx: minicppParser.IntTypeContext?): Type {
        return INT
    }

    override fun visitBoolType(ctx: minicppParser.BoolTypeContext?): Type {
        return BOOL
    }

    override fun visitVoidType(ctx: minicppParser.VoidTypeContext?): Type {
        return  VOID
    }
}
