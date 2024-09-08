package org.azauner.minicpp.ast.node

sealed interface ActionOperation

data class ArrayAccessOperation(val expr: Expr): ActionOperation

data class CallOperation(var actParList: List<Expr>): ActionOperation

enum class IncDec(val sourceCode: String) {
    INCREASE("++"),
    DECREASE("--")
}

