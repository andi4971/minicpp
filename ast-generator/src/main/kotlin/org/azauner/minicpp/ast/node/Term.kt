package org.azauner.minicpp.ast.node

data class Term(val firstNotFact: NotFact, val termEntries: List<TermEntry>)

data class TermEntry(val notFact: NotFact, val termOperator: TermOperator)

enum class TermOperator(val sourceCode: String) {
    MUL("*"),
    DIV("/"),
    MOD("%")
}
