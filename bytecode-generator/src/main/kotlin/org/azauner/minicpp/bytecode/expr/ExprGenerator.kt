package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.minicpp.ast.util.getIdent
import org.azauner.minicpp.ast.util.getType
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class ExprGenerator(private val mv: MethodVisitor) {

    private fun generateArrayIndex(firstExpr: OrExpr) {
       val fact =  getActionFact(firstExpr)
        ExprGenerator(mv).generate((fact.actionOp!! as ArrayAccessOperation).expr)
    }

    private fun getActionFact(expr: OrExpr): ActionFact {
        return expr.andExpressions.first().relExpressions.first().firstExpr.term.firstNotFact.fact as ActionFact
    }

    fun generate(expr: Expr) {
        val generator = OrExprGenerator(mv)
        if (expr.exprEntries.isEmpty()) {
            generator.generate(expr.firstExpr)
        } else {
            val entry = expr.exprEntries.first()

            if(isArrayExpr(expr.firstExpr)) {
                generateArrayAccess(expr)
            }else {
                generateVariableAccess(expr, entry)
            }
        }
    }

    private fun generateVariableAccess(leftHandExpr: Expr, rightHandExpr: ExprEntry) {
        if(rightHandExpr.assignOperator != AssignOperator.ASSIGN) {
            val variable = leftHandExpr.scope.getVariable(leftHandExpr.firstExpr.getIdent())
            mv.visitVarInsn(Opcodes.ILOAD, variable.index)
        }

        OrExprGenerator(mv).generate(rightHandExpr.orExpr)
        generateCalculation(rightHandExpr.assignOperator)
        storeValue(leftHandExpr.firstExpr.getIdent(), leftHandExpr.scope)
    }

    private fun generateArrayAccess(expr: Expr) {
        val arrVar  = expr.scope.getVariable(expr.firstExpr.getIdent())
        mv.visitVarInsn(Opcodes.ALOAD, arrVar.index)
        generateArrayIndex(expr.firstExpr)

        val rightHandExpr = expr.exprEntries.first()
        if(rightHandExpr.assignOperator != AssignOperator.ASSIGN) {
            mv.visitInsn(Opcodes.DUP2)
            mv.visitInsn(Opcodes.IALOAD)
        }

        OrExprGenerator(mv).generate(rightHandExpr.orExpr)
        generateCalculation(rightHandExpr.assignOperator)

        val firstExprType = expr.firstExpr.getType()
        val opCode = when(firstExprType == ExprType.INT) {
            true -> Opcodes.IASTORE
            false -> Opcodes.BASTORE
        }
        mv.visitInsn(opCode)
    }

    private fun generateCalculation(assignOperator: AssignOperator) {
        when (assignOperator) {
            AssignOperator.ADD_ASSIGN -> mv.visitInsn(Opcodes.IADD)
            AssignOperator.SUB_ASSIGN -> mv.visitInsn(Opcodes.ISUB)
            AssignOperator.MUL_ASSIGN -> mv.visitInsn(Opcodes.IMUL)
            AssignOperator.DIV_ASSIGN -> mv.visitInsn(Opcodes.IDIV)
            AssignOperator.MOD_ASSIGN -> mv.visitInsn(Opcodes.IREM)
            else -> ""
        }
    }

    private fun isArrayExpr(exprType: OrExpr): Boolean {
        val fact = exprType.andExpressions.first().relExpressions.first().firstExpr.term.firstNotFact.fact
        return fact is ActionFact && fact.actionOp is ArrayAccessOperation
    }

    private fun storeValue(ident: Ident, scope: Scope) {
        val variable = scope.getVariable(ident)
        val type = variable.type
        val index = variable.index
         storeAssignValue(type, index)
    }

    private fun storeAssignValue(varType: ExprType, index: Int) {
        when (varType) {
            ExprType.BOOL_ARR, ExprType.INT_ARR -> mv.visitVarInsn(Opcodes.ASTORE, index)
            else -> mv.visitVarInsn(Opcodes.ISTORE, index)
        }
    }
}
