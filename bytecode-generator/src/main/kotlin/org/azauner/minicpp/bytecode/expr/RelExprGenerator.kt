package org.azauner.minicpp.bytecode.expr


import org.azauner.minicpp.ast.node.RelExpr
import org.azauner.minicpp.ast.node.RelOperator
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class RelExprGenerator(private val mv: MethodVisitor) {

    fun generate(relExpr: RelExpr) {
        val generator = SimpleExprGenerator(mv)
        generator.generate(relExpr.firstExpr)

        relExpr.relExprEntries.forEach { entry ->
            generator.generate(entry.simpleExpr)
            generateComparison(entry.relOperator)
        }

    }

    private fun generateComparison(relOperator: RelOperator) {
        val trueLabel = Label()
        val endLabel = Label()
        when (relOperator) {
            RelOperator.EQUAL -> mv.visitJumpInsn(IF_ICMPNE, trueLabel)
            RelOperator.NOT_EQUAL -> mv.visitJumpInsn(IF_ICMPEQ, trueLabel)
            RelOperator.LESS_THAN_EQUAL -> mv.visitJumpInsn(IF_ICMPGT, trueLabel)
            RelOperator.LESS_THAN -> mv.visitJumpInsn(IF_ICMPGE, trueLabel)
            RelOperator.GREATER_THAN_EQUAL -> mv.visitJumpInsn(IF_ICMPLT, trueLabel)
            RelOperator.GREATER_THAN -> mv.visitJumpInsn(IF_ICMPLE, trueLabel)
        }
        mv.visitInsn(ICONST_1)
        mv.visitJumpInsn(GOTO, endLabel)

        mv.visitLabel(trueLabel)
        mv.visitInsn(ICONST_0)

        mv.visitLabel(endLabel)
    }
}
