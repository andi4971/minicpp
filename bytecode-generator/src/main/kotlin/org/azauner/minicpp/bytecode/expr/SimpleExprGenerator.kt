package org.azauner.minicpp.bytecode.expr

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.INEG

class SimpleExprGenerator(private val mv: MethodVisitor) {

    fun generate(simpleExpr: org.azauner.minicpp.ast.node.SimpleExpr, shouldEmitValue: Boolean = true) {
        val generator = TermGenerator(mv)
        generator.generate(simpleExpr.term, shouldEmitValue)
        simpleExpr.sign?.let {
            if (it == org.azauner.minicpp.ast.node.Sign.MINUS) {
                mv.visitInsn(INEG)
            }
        }
        simpleExpr.simpleExprEntries.forEach { simpleExprEntry ->
            generator.generate(simpleExprEntry.term)
            val operator = when(simpleExprEntry.sign) {
                org.azauner.minicpp.ast.node.Sign.PLUS -> org.objectweb.asm.Opcodes.IADD
                org.azauner.minicpp.ast.node.Sign.MINUS -> org.objectweb.asm.Opcodes.ISUB
            }
            mv.visitInsn(operator)
        }
    }
}
