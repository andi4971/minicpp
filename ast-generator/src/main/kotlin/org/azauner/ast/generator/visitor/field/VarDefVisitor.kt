package org.azauner.ast.generator.visitor.field

import org.azauner.ast.generator.visitor.IdentVisitor
import org.azauner.ast.generator.visitor.TypeVisitor
import org.azauner.ast.node.ExprType
import org.azauner.ast.node.VarDef
import org.azauner.ast.node.VarDefEntry
import org.azauner.ast.node.scope.Scope
import org.azauner.ast.util.getExprType
import org.azauner.ast.util.requireSemantic
import org.azauner.ast.util.toArrayType
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class VarDefVisitor(private val scope: Scope) : minicppBaseVisitor<VarDef>() {
    override fun visitVarDef(ctx: minicppParser.VarDefContext): VarDef {
        val type = ctx.type().accept(TypeVisitor())
        val idents = ctx.varDefEntry().map { entry ->
            val ident = entry.IDENT().accept(IdentVisitor())
            val init = entry.init()?.accept(InitVisitor())

            VarDefEntry(
                ident = ident,
                pointer = entry.STAR() != null,
                value = init
            )
        }
        idents.forEach { node ->
            validateNodeInit(node, type)
            val variableType = if (node.pointer) {
                type.toArrayType()
            } else {
                type
            }
            scope.addVariable(node.ident, variableType, const = false)
        }

        return VarDef(type, idents)
    }

    private fun validateNodeInit(node: VarDefEntry, type: ExprType) {
        node.value?.let {
            val initType = it.value.getExprType()
            if(initType == ExprType.NULLPTR) {
                requireSemantic(node.pointer) {
                    "$type is not a pointer type"
                }
            }else {
                requireSemantic(initType == type) {
                    "Type mismatch: $initType != $type"
                }
            }
        }
    }
}

