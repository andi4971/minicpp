package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.Term
import org.azauner.minicpp.ast.node.TermOperator
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class TermGenerator(private val mv: MethodVisitor) {

    fun generate(term: Term) {
        val generator = NotFactGenerator(mv)
        generator.generate(term.firstNotFact)
        term.termEntries.forEach { termEntry ->
            generator.generate(termEntry.notFact)
            val operator = when(termEntry.termOperator) {
                TermOperator.MUL -> IMUL
                TermOperator.DIV -> IDIV
                TermOperator.MOD -> IREM
            }
            mv.visitInsn(operator)
        }
    }
}



