package org.azauner.ast.node


data class Expr(val firstExpr: OrExpr, val exprEntries: List<ExprEntry>) : OutputStatEntry

data class ExprEntry(val orExpr: OrExpr, val assignOperator: AssignOperator)

enum class AssignOperator(val sourceCode: String) {
    ASSIGN ("="),
    ADD_ASSIGN("+="),
    SUB_ASSIGN("-="),
    MUL_ASSIGN("*="),
    DIV_ASSIGN("/="),
    MOD_ASSIGN("%=")
}

