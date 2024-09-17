package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.Sign
import org.azauner.minicpp.ast.node.SimpleExpr
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.INEG

class SimpleExprGenerator(private val mv: MethodVisitor) {

    fun generate(simpleExpr: SimpleExpr) {
        val generator = TermGenerator(mv)
        generator.generate(simpleExpr.term)
        simpleExpr.sign?.let {
            if (it == Sign.MINUS) {
                mv.visitInsn(INEG)
            }
        }
        simpleExpr.simpleExprEntries.forEach { simpleExprEntry ->
            generator.generate(simpleExprEntry.term)
            val operator = when(simpleExprEntry.sign) {
                Sign.PLUS -> org.objectweb.asm.Opcodes.IADD
                Sign.MINUS -> org.objectweb.asm.Opcodes.ISUB
            }
            mv.visitInsn(operator)
        }
    }
}
