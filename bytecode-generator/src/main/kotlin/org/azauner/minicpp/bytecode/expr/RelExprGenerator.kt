package org.azauner.minicpp.bytecode.expr


import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class RelExprGenerator(private val mv: MethodVisitor) {

    fun generate(relExpr: org.azauner.minicpp.ast.node.RelExpr, shouldEmitValue: Boolean = true) {
        val generator = SimpleExprGenerator(mv)
        generator.generate(relExpr.firstExpr, shouldEmitValue)

        relExpr.relExprEntries.forEach { entry ->
            generator.generate(entry.simpleExpr)
            generateComparison(entry.relOperator)
        }

    }

    private fun generateComparison(relOperator: org.azauner.minicpp.ast.node.RelOperator) {
        val trueLabel = Label()
        val endLabel = Label()
        when (relOperator) {
            org.azauner.minicpp.ast.node.RelOperator.EQUAL -> mv.visitJumpInsn(IF_ICMPNE, trueLabel)
            org.azauner.minicpp.ast.node.RelOperator.NOT_EQUAL -> mv.visitJumpInsn(IF_ICMPEQ, trueLabel)
            org.azauner.minicpp.ast.node.RelOperator.LESS_THAN_EQUAL -> mv.visitJumpInsn(IF_ICMPGT, trueLabel)
            org.azauner.minicpp.ast.node.RelOperator.LESS_THAN -> mv.visitJumpInsn(IF_ICMPGE, trueLabel)
            org.azauner.minicpp.ast.node.RelOperator.GREATER_THAN_EQUAL -> mv.visitJumpInsn(IF_ICMPLT, trueLabel)
            org.azauner.minicpp.ast.node.RelOperator.GREATER_THAN -> mv.visitJumpInsn(IF_ICMPLE, trueLabel)
        }
        mv.visitInsn(ICONST_1)
        mv.visitJumpInsn(GOTO, endLabel)

        mv.visitLabel(trueLabel)
        mv.visitInsn(ICONST_0)

        mv.visitLabel(endLabel)
    }
}
