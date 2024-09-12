package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.AndExpr
import org.objectweb.asm.MethodVisitor

class AndExprGenerator(private val mv: MethodVisitor) {

    fun generate(andExpr: AndExpr) {
        if(andExpr.relExpressions.size == 1) {
            RelExprGenerator(mv).generate(andExpr.relExpressions.first())
        } else {
         /*   val label = mv.visitLabel()
            andExpr.relExpressions.forEach {
                RelExprGenerator(mv).generate(it)
                mv.visitJumpInsn(IFEQ, label)
            }*/
        }
    }
}

