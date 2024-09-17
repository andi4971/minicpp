package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.minicpp.ast.util.getType
import org.azauner.minicpp.bytecode.MiniCppGenerator.Companion.className
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class ActionFactGenerator(private val mv: MethodVisitor) {
    fun generate(actionFact: ActionFact) {
        when(actionFact.actionOp) {
            null -> {
                val variable = actionFact.scope.getVariable(actionFact.ident)
                mv.visitVarInsn(Opcodes.ILOAD, variable.index)
            }
            is ArrayAccessOperation -> generateArrayAccess(actionFact.actionOp as ArrayAccessOperation, actionFact.scope, actionFact.ident)
            is CallOperation ->generateFunctionCall(actionFact.actionOp as CallOperation, actionFact.scope, actionFact.ident)
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

    private fun generateArrayAccess(actionOp: ArrayAccessOperation, scope: Scope, ident: Ident) {
        val variable = scope.getVariable(ident)
        mv.visitVarInsn(Opcodes.ALOAD, variable.index)
        //index
        ExprGenerator(mv).generate(actionOp.expr)
        mv.visitInsn(Opcodes.IALOAD)
    }
}
