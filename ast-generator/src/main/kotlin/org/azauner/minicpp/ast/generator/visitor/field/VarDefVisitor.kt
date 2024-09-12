package org.azauner.minicpp.ast.generator.visitor.field

import org.azauner.minicpp.ast.generator.visitor.IdentVisitor
import org.azauner.minicpp.ast.generator.visitor.TypeVisitor
import org.azauner.minicpp.ast.node.ExprType
import org.azauner.minicpp.ast.node.Init
import org.azauner.minicpp.ast.node.VarDef
import org.azauner.minicpp.ast.node.VarDefEntry
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.minicpp.ast.util.getExprType
import org.azauner.minicpp.ast.util.requireSemantic
import org.azauner.minicpp.ast.util.toPointerTypeOptional
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class VarDefVisitor(private val scope: Scope) : minicppBaseVisitor<VarDef>() {
    override fun visitVarDef(ctx: minicppParser.VarDefContext): VarDef {
        val type = ctx.type().accept(TypeVisitor())
        val idents = ctx.varDefEntry().map { entry ->
            val ident = entry.IDENT().accept(IdentVisitor())
            val pointer = entry.STAR() != null
            val init = entry.init()?.accept(InitVisitor())?.also { validateNodeInit(it, pointer, type) }

            VarDefEntry(
                ident = ident,
                pointer = entry.STAR() != null,
                value = init,
                variable = scope.addVariable(ident, type.toPointerTypeOptional(pointer), const = false)
            )
        }

        return VarDef(type, idents)
    }

    private fun validateNodeInit(value: Init, isPointer: Boolean, type: ExprType) {

        val initType = value.value.getExprType()
        if (initType == ExprType.NULLPTR) {
            requireSemantic(isPointer) {
                "$type is not a pointer type"
            }
        } else {
            requireSemantic(initType == type) {
                "Type mismatch: $initType != $type"
            }
        }
    }
}


