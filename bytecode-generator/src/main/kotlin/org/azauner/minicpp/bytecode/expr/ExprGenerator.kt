package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.ast.node.scope.Variable
import org.azauner.minicpp.ast.util.getIdent
import org.azauner.minicpp.ast.util.mapToAssignPairs
import org.azauner.minicpp.bytecode.MiniCppGenerator.Companion.className
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.BASTORE
import org.objectweb.asm.Opcodes.IASTORE

class ExprGenerator(private val mv: MethodVisitor) {

    private fun OrExpr.getFact(): Fact {
        return andExpressions.first().relExpressions.first().firstExpr.term.firstNotFact.fact
    }

    private fun OrExpr.isArrayAccess(): Boolean {
        val fact = getFact()
        return fact is ActionFact && fact.actionOp is ArrayAccessOperation
    }

    fun generate(expr: Expr, shouldEmitValue: Boolean = true) {
        val generator = OrExprGenerator(mv)
        if (expr.exprEntries.isEmpty()) {
            generator.generate(expr.firstExpr, shouldEmitValue)
        } else {

            val exprEntries = expr.mapToAssignPairs()

            generator.putExprValuesOnStack(exprEntries)

            generator.generateAssignCode(exprEntries)

        }
    }

    private fun OrExprGenerator.generateAssignCode(exprEntries: List<Pair<OrExpr, AssignOperator?>>) {
        val entriesReversed = exprEntries.reversed()
        entriesReversed.forEachIndexed { index, exprEntry ->
            if (exprEntry.second == null) {
                //no assign just generate value
                this.generate(exprEntry.first, true)
            } else {
                val assignOperator = exprEntry.second!!
                generateCalculation(assignOperator)
                if (index != entriesReversed.lastIndex) {
                    if (exprEntry.first.isArrayAccess()) {
                        mv.visitInsn(Opcodes.DUP_X2)
                    } else {
                        mv.visitInsn(Opcodes.DUP)

                    }
                }
                //assign value from stack to variable
                generateVariableStore(exprEntry.first)
            }
        }
    }


    private fun OrExprGenerator.putExprValuesOnStack(exprEntries: List<Pair<OrExpr, AssignOperator?>>) {

        exprEntries.filter { entry ->
            entry.second != null
        }.forEach {
            if (it.first.isArrayAccess()) {
                if (it.second == AssignOperator.ASSIGN) {
                    ActionFactGenerator.skipLoadOfNextArray = true
                } else {
                    ActionFactGenerator.duplicateNextArrayIndex = true
                }
                this.generate(it.first)
            } else {
                if (it.second != AssignOperator.ASSIGN) {
                    this.generate(it.first)
                }

            }
        }
    }

    private fun generateVariableStore(orExpr: OrExpr) {
        if (orExpr.isArrayAccess()) {
            val variable = orExpr.scope.getVariable(orExpr.getIdent())
            if(variable.type == ExprType.INT_ARR) {
                mv.visitInsn(IASTORE)
            } else {
                mv.visitInsn(BASTORE)
            }
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
            AssignOperator.ASSIGN -> ""
        }
    }

    private fun storeValue(orExpr: OrExpr) {
        val ident = orExpr.getIdent()
        val variable = orExpr.scope.getVariable(ident)
        val type = variable.type
        val index = variable.index
        if(variable.static) {
            storeStatic(variable)
        }else {
            storeAssignValue(type, index)
        }
    }

    private fun storeStatic(variable: Variable) {
        mv.visitFieldInsn(
            Opcodes.PUTSTATIC,
            className,
            variable.ident.name,
            variable.type.descriptor
        )
    }

    private fun storeAssignValue(varType: ExprType, index: Int) {
        when (varType) {
            ExprType.BOOL_ARR, ExprType.INT_ARR -> mv.visitVarInsn(Opcodes.ASTORE, index)
            else -> mv.visitVarInsn(Opcodes.ISTORE, index)
        }
    }
}
