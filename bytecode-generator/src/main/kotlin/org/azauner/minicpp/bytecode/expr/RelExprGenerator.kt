package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.RelExpr
import org.objectweb.asm.MethodVisitor

class RelExprGenerator(private val mv: MethodVisitor) {

        fun generate(relExpr: RelExpr) {
            if(relExpr.relExprEntries.isEmpty()) {
                SimpleExprGenerator(mv).generate(relExpr.firstExpr)
            } else {
                /*val label = mv.visitLabel()
                relExpr.addExpressions.forEach {
                    AddExprGenerator(mv).generate(it)
                    mv.visitJumpInsn(IFEQ, label)
                }*/
            }
        }
}
