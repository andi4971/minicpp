package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class RelExpr(val firstExpr: SimpleExpr, val relExprEntries: List<RelExprEntry>): SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        firstExpr.generateSourceCode(sb)
        relExprEntries.forEach {
            sb.append(" ")
            sb.append(it.relOperator.sourceCode)
            sb.append(" ")
            it.simpleExpr.generateSourceCode(sb)
        }
    }
}

data class RelExprEntry(val simpleExpr: SimpleExpr, val relOperator: RelOperator)


enum class RelOperator(val sourceCode: String) {
    EQUAL("=="),
    NOT_EQUAL("!="),
    LESS_THAN_EQUAL("<="),
    LESS_THAN("<"),
    GREATER_THAN_EQUAL(">="),
    GREATER_THAN(">")
}

val boolRelOperators = listOf(RelOperator.EQUAL, RelOperator.NOT_EQUAL)
