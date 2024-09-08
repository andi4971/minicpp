package org.azauner.ast.node


data class RelExpr(val firstExpr: SimpleExpr, val relExprEntries: List<RelExprEntry>)

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
