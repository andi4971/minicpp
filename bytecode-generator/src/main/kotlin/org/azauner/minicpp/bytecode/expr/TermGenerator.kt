package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.Sign
import org.azauner.minicpp.ast.node.Term
import org.objectweb.asm.MethodVisitor

class TermGenerator(private val mv: MethodVisitor) {

    fun generate(term: Term, sign: Sign?) {
        if(term.termEntries.isEmpty()) {
            NotFactGenerator(mv).generate(term.firstNotFact)
        } else {
           /* val iterator = term.factors.iterator()
            FactorGenerator(mv).generate(iterator.next(), sign)
            while(iterator.hasNext()) {
                val factor = iterator.next()
                FactorGenerator(mv).generate(factor, sign)
                mv.visitInsn(sign.opcode)
            }*/
        }
    }
}
