package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.minicpp.ast.util.getType
import org.azauner.minicpp.bytecode.MiniCppGenerator.Companion.className
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class ActionFactGenerator(private val mv: MethodVisitor) {

    companion object {
        var duplicateNextArrayIndex = false
        var skipLoadOfNextArray = false
    }

    fun generate(actionFact: ActionFact) {
        when (actionFact.actionOp) {
            null -> {
                generateVariableAccess(actionFact)
            }

            is ArrayAccessOperation -> generateArrayAccess(
                actionFact
            )

            is CallOperation -> generateFunctionCall(
                actionFact.actionOp as CallOperation,
                actionFact.scope,
                actionFact.ident
            )
        }
    }

    private fun generateVariableAccess(actionFact: ActionFact) {
        val variable = actionFact.scope.getVariable(actionFact.ident)

        actionFact.prefix?.let {
            iInc(variable.index, it)
        }

        mv.visitVarInsn(ILOAD, variable.index)

        actionFact.suffix?.let {
            iInc(variable.index, it)
        }
    }

    private fun iInc(index: Int, incDec: IncDec) {
        when (incDec) {
            IncDec.INCREASE -> mv.visitIincInsn(index, 1)
            IncDec.DECREASE -> mv.visitIincInsn(index, -1)
        }
    }

    private fun generateFunctionCall(callOperation: CallOperation, scope: Scope, ident: Ident) {
        val exprGenerator = ExprGenerator(mv)
        callOperation.actParList.forEach {
            exprGenerator.generate(it)
        }
        val func = scope.getFunction(ident, callOperation.actParList.map { it.getType() })
        val descriptor = getDescriptor(callOperation.actParList.map { it.getType() }, func.returnType)
        mv.visitMethodInsn(INVOKESTATIC, className, ident.name, descriptor, false)
    }

    private fun getDescriptor(actParTypes: List<ExprType>, returnType: ExprType): String {
        val sb = StringBuilder()
        sb.append("(")
        actParTypes.forEach {
            sb.append(it.descriptor)
        }
        sb.append(")")
        sb.append(returnType.descriptor)
        return sb.toString()
    }

    private fun generateArrayAccess(actionFact: ActionFact) {
        actionFact.run {
            val variable = scope.getVariable(ident)
            mv.visitVarInsn(ALOAD, variable.index)

            val arrayAccessOp = actionOp as ArrayAccessOperation
            ExprGenerator(mv).generate(arrayAccessOp.expr)
            if(duplicateNextArrayIndex){
                mv.visitInsn(DUP2)
                duplicateNextArrayIndex = false
            }

            if(prefix != null || suffix != null) {
                prefix?.let { iIncArr(it, isPrefix = true) }
                suffix?.let { iIncArr(it, isPrefix = false) }
            }else {
                if(skipLoadOfNextArray) {
                    skipLoadOfNextArray = false
                }else {
                    mv.visitInsn(IALOAD)
                }
            }
        }

    }

    private fun iIncArr(incDec: IncDec, isPrefix: Boolean) {
        mv.visitInsn(DUP2)
        mv.visitInsn(IALOAD)
        if(!isPrefix) {
            mv.visitInsn(DUP_X2)
        }
        when (incDec) {
            IncDec.INCREASE -> mv.visitInsn(ICONST_1)
            IncDec.DECREASE -> mv.visitInsn(ICONST_M1)
        }
        mv.visitInsn(IADD)
        if(isPrefix) {
            mv.visitInsn(DUP_X2)
        }
        mv.visitInsn(IASTORE)
    }
}
