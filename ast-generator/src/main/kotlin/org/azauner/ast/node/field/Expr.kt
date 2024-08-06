package org.azauner.ast.node.field

data class Expr(val firstExpr: OrExpr, val exprEntries: List<ExprEntry>) : OutputStatEntry()

data class ExprEntry(val orExpr: OrExpr, val exprOperator: ExprOperator)

enum class ExprOperator {
    ASSIGN,
    ADD_ASSIGN,
    SUB_ASSIGN,
    MUL_ASSIGN,
    DIV_ASSIGN,
    MOD_ASSIGN
}


data class OrExpr(val andExprs: List<AndExpr>)

data class AndExpr(val relExprs: List<RelExpr>)

data class RelExpr(val)

