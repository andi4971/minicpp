package org.azauner.minicpp.ast.node


data class RelExpr(
    val firstExpr: org.azauner.minicpp.ast.node.SimpleExpr,
    val relExprEntries: List<org.azauner.minicpp.ast.node.RelExprEntry>
)

data class RelExprEntry(
    val simpleExpr: org.azauner.minicpp.ast.node.SimpleExpr,
    val relOperator: org.azauner.minicpp.ast.node.RelOperator
)


enum class RelOperator(val sourceCode: String) {
    EQUAL("=="),
    NOT_EQUAL("!="),
    LESS_THAN_EQUAL("<="),
    LESS_THAN("<"),
    GREATER_THAN_EQUAL(">="),
    GREATER_THAN(">")
}

val boolRelOperators = listOf(
    org.azauner.minicpp.ast.node.RelOperator.EQUAL,
    org.azauner.minicpp.ast.node.RelOperator.NOT_EQUAL
)
