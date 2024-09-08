package org.azauner.ast.node


data class SimpleExpr(val sign: Sign?, val term: Term, val simpleExprEntries: List<SimpleExprEntry>)

enum class Sign(val sourceCode: String) {
    PLUS("+"),
    MINUS("-")
}

data class SimpleExprEntry(val sign: Sign, val term: Term)
