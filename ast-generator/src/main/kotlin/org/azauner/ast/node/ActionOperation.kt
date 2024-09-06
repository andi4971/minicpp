package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator


sealed interface ActionOperation: SourceCodeGenerator

data class ArrayAccessOperation(val expr: Expr): ActionOperation {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("[")
        expr.generateSourceCode(sb)
        sb.append("]")
    }
}

data class CallOperation(var actParList: List<Expr>): ActionOperation {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("(")
        actParList.forEachIndexed { index, expr ->
            expr.generateSourceCode(sb)
            if (index != actParList.lastIndex) {
                sb.append(", ")
            }
        }
        sb.append(")")
    }
}


enum class IncDec(val sourceCode: String) {
    INCREASE("++"),
    DECREASE("--")
}

