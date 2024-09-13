package org.azauner.minicpp.ast.node

import org.azauner.minicpp.ast.node.scope.Scope

sealed interface ActionOperation

data class ArrayAccessOperation(val expr: Expr, val scope: Scope): ActionOperation

data class CallOperation(var actParList: List<Expr>, val scope: Scope): ActionOperation

enum class IncDec(val sourceCode: String) {
    INCREASE("++"),
    DECREASE("--")
}

