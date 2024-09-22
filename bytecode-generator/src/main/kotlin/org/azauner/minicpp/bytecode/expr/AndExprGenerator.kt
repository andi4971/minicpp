package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.AndExpr
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class AndExprGenerator(private val mv: MethodVisitor) {

    fun generate(andExpr: AndExpr) {
        RelExprGenerator(mv).generate(andExpr.relExpressions.first())

        if (andExpr.relExpressions.isNotEmpty()) {

            val trueLabel = Label()
            val endLabel = Label()
            mv.visitJumpInsn(IFEQ, trueLabel)

            andExpr.relExpressions.drop(1).forEach {
                RelExprGenerator(mv).generate(it)
                mv.visitJumpInsn(IFEQ, trueLabel)
            }

            mv.visitInsn(ICONST_1)
            mv.visitJumpInsn(GOTO, endLabel)

            mv.visitLabel(trueLabel)
            mv.visitInsn(ICONST_0)

            mv.visitLabel(endLabel)
        }

    }
}

