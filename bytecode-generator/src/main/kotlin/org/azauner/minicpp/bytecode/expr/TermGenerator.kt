package org.azauner.minicpp.bytecode.expr

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class TermGenerator(private val mv: MethodVisitor) {

    fun generate(term: org.azauner.minicpp.ast.node.Term, shouldEmitValue: Boolean = true) {
        val generator = NotFactGenerator(mv)
        generator.generate(term.firstNotFact, shouldEmitValue)
        term.termEntries.forEach { termEntry ->
            generator.generate(termEntry.notFact)
            val operator = when(termEntry.termOperator) {
                org.azauner.minicpp.ast.node.TermOperator.MUL -> IMUL
                org.azauner.minicpp.ast.node.TermOperator.DIV -> IDIV
                org.azauner.minicpp.ast.node.TermOperator.MOD -> IREM
            }
            mv.visitInsn(operator)
        }
    }
}



