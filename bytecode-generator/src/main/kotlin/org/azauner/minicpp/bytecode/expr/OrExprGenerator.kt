package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.OrExpr
import org.objectweb.asm.MethodVisitor

class OrExprGenerator(private val mv: MethodVisitor) {

    fun generate(orExpr: OrExpr) {
        if(orExpr.andExpressions.size == 1) {
            AndExprGenerator(mv).generate(orExpr.andExpressions.first())
        } else {
            /*val label = mv.visitLabel()
            orExpr.andExpressions.forEach {
                AndExprGenerator(mv).generate(it)
                mv.visitJumpInsn(IFEQ, label)
            }*/
        }
    }
}
