package org.azauner.minicpp.ast.node

import org.azauner.minicpp.ast.node.scope.Scope


data class Expr(val firstExpr: OrExpr, val exprEntries: List<ExprEntry>, val scope: Scope) : OutputStatEntry

data class ExprEntry(val orExpr: OrExpr, val assignOperator: AssignOperator)

enum class AssignOperator(val sourceCode: String) {
    ASSIGN ("="),
    ADD_ASSIGN("+="),
    SUB_ASSIGN("-="),
    MUL_ASSIGN("*="),
    DIV_ASSIGN("/="),
    MOD_ASSIGN("%=")
}

