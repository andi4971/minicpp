package org.azauner.minicpp.ast.node


data class Expr(
    val firstExpr: org.azauner.minicpp.ast.node.OrExpr,
    val exprEntries: List<org.azauner.minicpp.ast.node.ExprEntry>,
    val scope: org.azauner.minicpp.ast.node.scope.Scope
) :
    org.azauner.minicpp.ast.node.OutputStatEntry

data class ExprEntry(
    val orExpr: org.azauner.minicpp.ast.node.OrExpr,
    val assignOperator: org.azauner.minicpp.ast.node.AssignOperator
)

enum class AssignOperator(val sourceCode: String) {
    ASSIGN("="),
    ADD_ASSIGN("+="),
    SUB_ASSIGN("-="),
    MUL_ASSIGN("*="),
    DIV_ASSIGN("/="),
    MOD_ASSIGN("%=")
}

