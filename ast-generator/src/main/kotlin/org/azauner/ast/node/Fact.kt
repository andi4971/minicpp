package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

sealed interface Fact : SourceCodeGenerator

data class ExprFact(val expr: Expr): Fact {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("(")
        expr.generateSourceCode(sb)
        sb.append(")")
    }
}

data class NewArrayTypeFact(val type: ExprType, val expr: Expr): Fact {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("new ")
        type.generateSourceCode(sb)
        sb.append("[")
        expr.generateSourceCode(sb)
        sb.append("]")
    }
}

data class ActionFact(val prefix: IncDec?, val ident: Ident, val actionOp: ActionOperation?, val suffix: IncDec?):
    Fact {
    override fun generateSourceCode(sb: StringBuilder) {
        prefix?.let {
            sb.append(it.sourceCode)
        }
        sb.append(ident.name)
        actionOp?.generateSourceCode(sb)
        suffix?.let {
            sb.append(it.sourceCode)
        }
    }
}
