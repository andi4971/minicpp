package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.NotFact
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ICONST_1
import org.objectweb.asm.Opcodes.IXOR

class NotFactGenerator(private val mv: MethodVisitor) {

    fun generate(notFact: NotFact) {
       FactGenerator(mv).generate(notFact.fact)

        if(notFact.negated) {
            mv.visitInsn(ICONST_1)
            mv.visitInsn(IXOR)
        }
    }
}
