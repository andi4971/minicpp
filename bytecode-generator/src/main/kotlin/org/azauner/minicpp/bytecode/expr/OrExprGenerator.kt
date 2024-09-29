package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.OrExpr
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class OrExprGenerator(private val mv: MethodVisitor) {

    fun generate(orExpr: OrExpr, shouldEmitValue: Boolean = true) {

        AndExprGenerator(mv).generate(orExpr.andExpressions.first(), shouldEmitValue)

        if (orExpr.andExpressions.size > 1) {
            val trueLabel = Label()
            val endLabel = Label()
            val falseLabel = Label()
            mv.visitJumpInsn(IFNE, trueLabel)

            orExpr.andExpressions.drop(1).dropLast(1).forEach {
                AndExprGenerator(mv).generate(it)
                mv.visitJumpInsn(IFNE, trueLabel)
            }
            orExpr.andExpressions.last().let {
                AndExprGenerator(mv).generate(it)
                mv.visitJumpInsn(IFEQ, falseLabel)
            }

            mv.visitLabel(trueLabel)
            mv.visitInsn(ICONST_1)
            mv.visitJumpInsn(GOTO, endLabel)

            mv.visitLabel(falseLabel)
            mv.visitInsn(ICONST_0)

            mv.visitLabel(endLabel)
        }
    }
}
