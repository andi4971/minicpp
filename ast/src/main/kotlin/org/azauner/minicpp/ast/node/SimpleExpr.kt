package org.azauner.minicpp.ast.node


data class SimpleExpr(
    val sign: org.azauner.minicpp.ast.node.Sign?,
    val term: org.azauner.minicpp.ast.node.Term,
    val simpleExprEntries: List<org.azauner.minicpp.ast.node.SimpleExprEntry>
)

enum class Sign(val sourceCode: String) {
    PLUS("+"),
    MINUS("-")
}

data class SimpleExprEntry(val sign: org.azauner.minicpp.ast.node.Sign, val term: org.azauner.minicpp.ast.node.Term)
