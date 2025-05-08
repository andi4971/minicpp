package org.azauner.minicpp.bytecode.expr


import org.azauner.minicpp.ast.node.RelOperator
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

    private fun generateComparison(relOperator: RelOperator) {
        val falseLabel = Label()
        val endLabel = Label()
        when (relOperator) {
            RelOperator.EQUAL -> mv.visitJumpInsn(IF_ICMPNE, falseLabel)
            RelOperator.NOT_EQUAL -> mv.visitJumpInsn(IF_ICMPEQ, falseLabel)
            RelOperator.LESS_THAN_EQUAL -> mv.visitJumpInsn(IF_ICMPGT, falseLabel)
            RelOperator.LESS_THAN -> mv.visitJumpInsn(IF_ICMPGE, falseLabel)
            RelOperator.GREATER_THAN_EQUAL -> mv.visitJumpInsn(IF_ICMPLT, falseLabel)
            RelOperator.GREATER_THAN -> mv.visitJumpInsn(IF_ICMPLE, falseLabel)
        }
        mv.visitInsn(ICONST_1)
        mv.visitJumpInsn(GOTO, endLabel)

        mv.visitLabel(falseLabel)
        mv.visitInsn(ICONST_0)

        mv.visitLabel(endLabel)
    }
}
