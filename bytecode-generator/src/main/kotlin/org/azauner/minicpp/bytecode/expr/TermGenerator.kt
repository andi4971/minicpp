package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.Sign
import org.azauner.minicpp.ast.node.Term
import org.azauner.minicpp.ast.node.TermOperator
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class TermGenerator(private val mv: MethodVisitor) {

    fun generate(term: Term, sign: Sign?) {
        NotFactGenerator(mv).generate(term.firstNotFact)
        term.termEntries.forEach { termEntry ->
            NotFactGenerator(mv).generate(termEntry.notFact)
            val operator = when(termEntry.termOperator) {
                TermOperator.MUL -> IMUL
                TermOperator.DIV -> IDIV
                TermOperator.MOD -> IREM
            }
            mv.visitInsn(operator)
        }
        sign?.let {
            if (it == Sign.MINUS) {
                mv.visitInsn(INEG)
            }
        }
    }
}
