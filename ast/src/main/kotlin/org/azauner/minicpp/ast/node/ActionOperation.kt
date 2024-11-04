package org.azauner.minicpp.ast.node

sealed interface ActionOperation

data class ArrayAccessOperation(
    val expr: org.azauner.minicpp.ast.node.Expr,
    val scope: org.azauner.minicpp.ast.node.scope.Scope
) :
    org.azauner.minicpp.ast.node.ActionOperation

data class CallOperation(
    var actParList: List<org.azauner.minicpp.ast.node.Expr>,
    val scope: org.azauner.minicpp.ast.node.scope.Scope
) :
    org.azauner.minicpp.ast.node.ActionOperation

enum class IncDec(val sourceCode: String) {
    INCREASE("++"),
    DECREASE("--")
}

