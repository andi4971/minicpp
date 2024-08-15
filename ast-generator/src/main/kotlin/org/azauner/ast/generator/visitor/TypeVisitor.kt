package org.azauner.ast.generator.visitor
import org.azauner.ast.node.Type
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class TypeVisitor: minicppBaseVisitor<Type>() {

    override fun visitType(ctx: minicppParser.TypeContext): Type {
        return when {
            ctx.INT_LIT() != null -> Type.INT
            ctx.BOOL() != null -> Type.BOOL
            ctx.VOID() != null -> Type.VOID
            else -> throw IllegalStateException("Unknown type")
        }
    }
}
