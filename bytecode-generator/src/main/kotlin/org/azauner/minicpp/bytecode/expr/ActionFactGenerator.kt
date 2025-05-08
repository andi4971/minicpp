package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.minicpp.ast.node.scope.Variable
import org.azauner.minicpp.ast.util.getType
import org.azauner.minicpp.bytecode.MiniCppGenerator.Companion.className
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class ActionFactGenerator(private val mv: MethodVisitor) {

    companion object {
        var duplicateNextArrayIndex = false
        var skipLoadOfNextArray = false

        fun getDescriptor(
            actParTypes: List<org.azauner.minicpp.ast.node.ExprType>,
            returnType: org.azauner.minicpp.ast.node.ExprType
        ): String {
            val sb = StringBuilder()
            sb.append("(")
            actParTypes.forEach {
                sb.append(it.descriptor)
            }
            sb.append(")")
            sb.append(returnType.descriptor)
            return sb.toString()
        }
    }

    fun generate(actionFact: ActionFact, shouldEmitValue: Boolean = true) {
        when (actionFact.actionOp) {
            null -> {
                generateVariableAccess(actionFact, shouldEmitValue)
            }

            is ArrayAccessOperation -> generateArrayAccess(
                actionFact, shouldEmitValue
            )

            is CallOperation -> generateFunctionCall(
                actionFact.actionOp as org.azauner.minicpp.ast.node.CallOperation,
                actionFact.scope,
                actionFact.ident
            )
        }
    }

    private fun generateVariableAccess(actionFact: ActionFact, shouldEmitValue: Boolean) {
        val variable = actionFact.scope.getVariable(actionFact.ident)
        actionFact.prefix?.let { iIncDec(variable, it) }
        if (shouldEmitValue) {
            when {
                variable.static && !variable.const -> mv
                    .visitFieldInsn(GETSTATIC, className, variable.ident.name, variable.type.descriptor)

                variable.const -> mv.visitLdcInsn(variable.constValue)
                    variable.type in ARR_TYPES -> mv.visitVarInsn(ALOAD, variable.index)
                    else -> mv.visitVarInsn(ILOAD, variable.index)
            }
        }
        actionFact.suffix?.let { iIncDec(variable, it) }
    }

    private fun iIncDec(variable: Variable, incDec: IncDec) {
        if (variable.static) {
            mv.visitFieldInsn(GETSTATIC, className, variable.ident.name, variable.type.descriptor)
            when (incDec) {
                IncDec.INCREASE -> mv.visitInsn(ICONST_1)
                IncDec.DECREASE -> mv.visitInsn(ICONST_M1)
            }
            mv.visitInsn(IADD)
            mv.visitFieldInsn(PUTSTATIC, className, variable.ident.name, variable.type.descriptor)
        } else {
            when (incDec) {
                IncDec.INCREASE -> mv.visitIincInsn(variable.index, 1)
                IncDec.DECREASE -> mv.visitIincInsn(variable.index, -1)
            }
        }
    }

    private fun generateFunctionCall(
        callOperation: CallOperation,
        scope: Scope,
        ident: Ident
    ) {
        val exprGenerator = ExprGenerator(mv)
        callOperation.actParList.forEach {
            exprGenerator.generate(it)
        }
        val func = scope.getFunction(ident, callOperation.actParList.map { it.getType() })
        val descriptor = getDescriptor(callOperation.actParList.map { it.getType() }, func.returnType)
        mv.visitMethodInsn(INVOKESTATIC, className, ident.name, descriptor, false)
    }


    private fun generateArrayAccess(actionFact: org.azauner.minicpp.ast.node.ActionFact, shouldEmitValue: Boolean) {
        actionFact.run {
            val variable = scope.getVariable(ident)
            if (variable.static) {
                mv.visitFieldInsn(GETSTATIC, className, variable.ident.name, variable.type.descriptor)
            } else {
                mv.visitVarInsn(ALOAD, variable.index)

            }

            val arrayAccessOp = actionOp as org.azauner.minicpp.ast.node.ArrayAccessOperation
            ExprGenerator(mv).generate(arrayAccessOp.expr, true)
            if (duplicateNextArrayIndex) {
                mv.visitInsn(DUP2)
                duplicateNextArrayIndex = false
            }

            if (prefix != null || suffix != null) {
                prefix?.let { iIncArr(it, isPrefix = true) }
                suffix?.let { iIncArr(it, isPrefix = false) }
            } else {
                if (skipLoadOfNextArray) {
                    skipLoadOfNextArray = false
                } else {
                    if (shouldEmitValue) {
                        if (variable.type == org.azauner.minicpp.ast.node.ExprType.INT_ARR) {
                            mv.visitInsn(IALOAD)
                        } else {
                            mv.visitInsn(BALOAD)
                        }
                    }
                    Unit
                }
            }
        }

    }

    private fun iIncArr(incDec: org.azauner.minicpp.ast.node.IncDec, isPrefix: Boolean) {
        mv.visitInsn(DUP2)
        mv.visitInsn(IALOAD)
        if (!isPrefix) {
            mv.visitInsn(DUP_X2)
        }
        when (incDec) {
            org.azauner.minicpp.ast.node.IncDec.INCREASE -> mv.visitInsn(ICONST_1)
            org.azauner.minicpp.ast.node.IncDec.DECREASE -> mv.visitInsn(ICONST_M1)
        }
        mv.visitInsn(IADD)
        if (isPrefix) {
            mv.visitInsn(DUP_X2)
        }
        mv.visitInsn(IASTORE)
    }
}
