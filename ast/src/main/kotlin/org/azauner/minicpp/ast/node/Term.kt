package org.azauner.minicpp.ast.node

data class Term(
    val firstNotFact: org.azauner.minicpp.ast.node.NotFact,
    val termEntries: List<org.azauner.minicpp.ast.node.TermEntry>
)

data class TermEntry(
    val notFact: org.azauner.minicpp.ast.node.NotFact,
    val termOperator: org.azauner.minicpp.ast.node.TermOperator
)

enum class TermOperator(val sourceCode: String) {
    MUL("*"),
    DIV("/"),
    MOD("%")
}
