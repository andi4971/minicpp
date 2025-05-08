package org.azauner.minicpp.bytecode.expr

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class AndExprGenerator(private val mv: MethodVisitor) {

    fun generate(andExpr: org.azauner.minicpp.ast.node.AndExpr, shouldEmitValue: Boolean = true) {
        RelExprGenerator(mv).generate(andExpr.relExpressions.first(), shouldEmitValue)
        if (andExpr.relExpressions.size > 1) {
            val falseLabel = Label()
            val endLabel = Label()
            mv.visitJumpInsn(IFEQ, falseLabel)
            andExpr.relExpressions.drop(1).forEach {
                RelExprGenerator(mv).generate(it)
                mv.visitJumpInsn(IFEQ, falseLabel)
            }
            mv.visitInsn(ICONST_1)
            mv.visitJumpInsn(GOTO, endLabel)
            mv.visitLabel(falseLabel)
            mv.visitInsn(ICONST_0)
            mv.visitLabel(endLabel)
        }
    }
}

