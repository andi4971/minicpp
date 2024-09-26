package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.ast.util.getIdent
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.IASTORE

class ExprGenerator(private val mv: MethodVisitor) {

    private fun OrExpr.getFact(): Fact {
        return andExpressions.first().relExpressions.first().firstExpr.term.firstNotFact.fact
    }

    private fun OrExpr.isArrayAccess(): Boolean {
        val fact = getFact()
        return fact is ActionFact && fact.actionOp is ArrayAccessOperation
    }

    fun generate(expr: Expr) {
        val generator = OrExprGenerator(mv)
        if (expr.exprEntries.isEmpty()) {
            generator.generate(expr.firstExpr)
        } else {

            val exprEntries = (listOf(expr.firstExpr) + expr.exprEntries.map { it.orExpr })
                .mapIndexed { index, entry ->
                    entry to expr.exprEntries.getOrNull(index)?.assignOperator
                }


            exprEntries.filter { entry ->
                entry.second != null && entry.second != AssignOperator.ASSIGN
            }.forEach {
                if(it.first.isArrayAccess()) {
                    ActionFactGenerator.duplicateNextArrayIndex = true
                }
                    generator.generate(it.first)

                }

            val entriesReversed = exprEntries.reversed()
            entriesReversed.forEachIndexed {index, exprEntry ->
                if (exprEntry.second == null) {
                    //no assign just generate value
                    generator.generate(exprEntry.first)
                } else {
                    val assignOperator = exprEntry.second!!
                    generateCalculation(assignOperator)
                    if (index != entriesReversed.lastIndex) {
                        if(exprEntry.first.isArrayAccess()) {
                            mv.visitInsn(Opcodes.DUP_X2)
                        }else{
                            mv.visitInsn(Opcodes.DUP)

                        }
                    }
                    //assign value from stack to variable
                    generateVariableStore(exprEntry.first)
                }
            }
        }
    }

    private fun generateVariableStore(orExpr: OrExpr) {
        if (orExpr.isArrayAccess()) {
            mv.visitInsn(IASTORE)
        } else {
            storeValue(orExpr)
        }
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

    private fun storeValue(orExpr: OrExpr) {
        val ident = orExpr.getIdent()
        val variable = orExpr.scope.getVariable(ident)
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
