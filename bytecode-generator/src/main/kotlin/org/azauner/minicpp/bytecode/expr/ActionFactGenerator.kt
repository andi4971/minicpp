package org.azauner.minicpp.bytecode.expr

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

    fun generate(actionFact: org.azauner.minicpp.ast.node.ActionFact, shouldEmitValue: Boolean = true) {
        when (actionFact.actionOp) {
            null -> {
                generateVariableAccess(actionFact, shouldEmitValue)
            }

            is org.azauner.minicpp.ast.node.ArrayAccessOperation -> generateArrayAccess(
                actionFact, shouldEmitValue
            )

            is org.azauner.minicpp.ast.node.CallOperation -> generateFunctionCall(
                actionFact.actionOp as org.azauner.minicpp.ast.node.CallOperation,
                actionFact.scope,
                actionFact.ident
            )
        }
    }

    private fun generateVariableAccess(actionFact: org.azauner.minicpp.ast.node.ActionFact, shouldEmitValue: Boolean) {
        val variable = actionFact.scope.getVariable(actionFact.ident)
        actionFact.prefix?.let {
            iInc(variable, it)
        }

        if (shouldEmitValue) {
            when {
                variable.static && !variable.const -> mv.visitFieldInsn(GETSTATIC, className, variable.ident.name, variable.type.descriptor)
                variable.const -> mv.visitLdcInsn(variable.constValue)
                variable.type in org.azauner.minicpp.ast.node.ARR_TYPES -> mv.visitVarInsn(ALOAD, variable.index)
                else -> mv.visitVarInsn(ILOAD, variable.index)
            }

            /*if (variable.static && !variable.const) {
                mv.visitFieldInsn(GETSTATIC, className, variable.ident.name, variable.type.descriptor)
            } else {
                when {
                    variable.type in ARR_TYPES -> mv.visitVarInsn(ALOAD, variable.index)
                    else -> mv.visitVarInsn(ILOAD, variable.index)

                }
            }*/
        }

        actionFact.suffix?.let {
            iInc(variable, it)
        }

    }

    private fun iInc(variable: Variable, incDec: org.azauner.minicpp.ast.node.IncDec) {
        if (variable.static) {
            mv.visitFieldInsn(GETSTATIC, className, variable.ident.name, variable.type.descriptor)
            when (incDec) {
                org.azauner.minicpp.ast.node.IncDec.INCREASE -> mv.visitInsn(ICONST_1)
                org.azauner.minicpp.ast.node.IncDec.DECREASE -> mv.visitInsn(ICONST_M1)
            }
            mv.visitInsn(IADD)
            mv.visitFieldInsn(PUTSTATIC, className, variable.ident.name, variable.type.descriptor)
        } else {
            when (incDec) {
                org.azauner.minicpp.ast.node.IncDec.INCREASE -> mv.visitIincInsn(variable.index, 1)
                org.azauner.minicpp.ast.node.IncDec.DECREASE -> mv.visitIincInsn(variable.index, -1)
            }
        }

    }

    private fun generateFunctionCall(
        callOperation: org.azauner.minicpp.ast.node.CallOperation,
        scope: org.azauner.minicpp.ast.node.scope.Scope,
        ident: org.azauner.minicpp.ast.node.Ident
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
