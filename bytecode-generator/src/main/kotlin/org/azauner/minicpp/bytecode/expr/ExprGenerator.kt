package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.Expr
import org.objectweb.asm.MethodVisitor

class ExprGenerator(val methodVisitor: MethodVisitor) {

    fun generate(expr: Expr) {
        if (expr.exprEntries.isEmpty()) {
            OrExprGenerator(methodVisitor).generate(expr.firstExpr)
        }else {

        }
    }
}
