package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.ActionFact
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class ActionFactGenerator(private val mv: MethodVisitor) {
    fun generate(actionFact: ActionFact) {
        if(actionFact.actionOp == null) {
            val variable = actionFact.scope.getVariable(actionFact.ident)
            mv.visitVarInsn(Opcodes.ILOAD, variable.index)
        }
    }
}
