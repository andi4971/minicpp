package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.AssignOperator
import org.azauner.minicpp.ast.node.Expr
import org.azauner.minicpp.ast.node.ExprType
import org.azauner.minicpp.ast.node.Ident
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.minicpp.ast.util.getIdent
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class ExprGenerator(val methodVisitor: MethodVisitor) {

    fun generate(expr: Expr) {
        val generator = OrExprGenerator(methodVisitor)
        if (expr.exprEntries.isEmpty()) {
            generator.generate(expr.firstExpr)
        } else {

            val entry = expr.exprEntries.first()
            generator.generate(entry.orExpr)
            storeValue(entry.assignOperator, expr.firstExpr.getIdent(), expr.scope)
        }
    }

    private fun storeValue(assignOperator: AssignOperator, ident: Ident, scope: Scope) {
        val variable = scope.getVariable(ident)
        val type = variable.type
        val index = variable.index
        when (assignOperator) {
            AssignOperator.ASSIGN -> storeAssignValue(type, index)
            else -> ""
            /*AssignOperator.ADD_ASSIGN -> {
                methodVisitor.visitVarInsn(type.loadOpcode, index)
                methodVisitor.visitInsn(type.addOpcode)
                methodVisitor.visitVarInsn(type.storeOpcode, index)
            }
            AssignOperator.SUB_ASSIGN -> {
                methodVisitor.visitVarInsn(type.loadOpcode, index)
                methodVisitor.visitInsn(type.subOpcode)
                methodVisitor.visitVarInsn(type.storeOpcode, index)
            }
            AssignOperator.MUL_ASSIGN -> {
                methodVisitor.visitVarInsn(type.loadOpcode, index)
                methodVisitor.visitInsn(type.mulOpcode)
                methodVisitor.visitVarInsn(type.storeOpcode, index)
            }
            AssignOperator.DIV_ASSIGN -> {
                methodVisitor.visitVarInsn(type.loadOpcode, index)
                methodVisitor.visitInsn(type.divOpcode)
                methodVisitor.visitVarInsn(type.storeOpcode, index)
            }
            AssignOperator.MOD_ASSIGN -> {
                methodVisitor.visitVarInsn(type.loadOpcode, index)
                methodVisitor.visitInsn(type.remOpcode)
                methodVisitor.visitVarInsn(type.storeOpcode, index)
            }*/
        }
    }

    private fun storeAssignValue(varType: ExprType, index: Int) {
        when (varType) {
            ExprType.BOOL_ARR, ExprType.INT_ARR -> methodVisitor.visitVarInsn(Opcodes.ASTORE, index)
            else -> methodVisitor.visitVarInsn(Opcodes.ISTORE, index)
        }
    }
}
