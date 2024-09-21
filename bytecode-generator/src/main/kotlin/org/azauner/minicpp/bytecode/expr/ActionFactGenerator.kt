package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.minicpp.ast.util.getType
import org.azauner.minicpp.bytecode.MiniCppGenerator.Companion.className
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class ActionFactGenerator(private val mv: MethodVisitor) {
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

        //todo add mechanism to omit this if it not used
        mv.visitVarInsn(Opcodes.ILOAD, variable.index)

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
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, className, ident.name, descriptor, false)
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

    private fun generateArrayAccess(actionOp: ActionFact) {
        actionOp.run {
            val variable = scope.getVariable(ident)
            mv.visitVarInsn(Opcodes.ALOAD, variable.index)
            //index

            //todo ++ --
            ExprGenerator(mv).generate((this.actionOp as ArrayAccessOperation).expr)

            mv.visitInsn(Opcodes.IALOAD)
        }

    }
}
