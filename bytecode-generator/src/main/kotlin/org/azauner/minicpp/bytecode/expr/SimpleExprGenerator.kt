package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.SimpleExpr
import org.objectweb.asm.MethodVisitor

class SimpleExprGenerator(private val mv: MethodVisitor) {

    fun generate(simpleExpr: SimpleExpr) {
        if(simpleExpr.simpleExprEntries.isEmpty()) {
            TermGenerator(mv).generate(simpleExpr.term, simpleExpr.sign)
        } else {

        }
    }
}
