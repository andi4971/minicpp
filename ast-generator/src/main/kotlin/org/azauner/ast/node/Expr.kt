package org.azauner.ast.node

data class Expr(val firstExpr: OrExpr, val exprEntries: List<ExprEntry>) : OutputStatEntry

data class ExprEntry(val orExpr: OrExpr, val assignOperator: AssignOperator)

enum class AssignOperator {
    ASSIGN,
    ADD_ASSIGN, 
    SUB_ASSIGN,
    MUL_ASSIGN,
    DIV_ASSIGN,
    MOD_ASSIGN
}


data class OrExpr(val andExpressions: List<AndExpr>)

data class AndExpr(val relExpressions: List<RelExpr>)

data class RelExpr(val firstExpr: SimpleExpr, val relExprEntries: List<RelExprEntry>)

data class RelExprEntry(val simpleExpr: SimpleExpr, val relOperator: RelOperator)

enum class RelOperator {
    EQUAL,
    NOT_EQUAL,
    LESS_THAN_EQUAL,
    LESS_THAN,
    GREATER_THAN_EQUAL,
    GREATER_THAN
}

val numericRelOperators = RelOperator.entries.toList()

val boolRelOperators = listOf(RelOperator.EQUAL, RelOperator.NOT_EQUAL)

data class SimpleExpr(val sign: Sign?, val term: Term, val simpleExprEntries: List<SimpleExprEntry>)

enum class Sign {
    PLUS,
    MINUS
}

data class SimpleExprEntry(val sign: Sign, val term: Term)

data class Term(val firstNotFact: NotFact, val termEntries: List<TermEntry>)

data class TermEntry(val notFact: NotFact, val termOperator: TermOperator)

enum class TermOperator {
    MUL,
    DIV,
    MOD
}

data class NotFact(val negated: Boolean, val fact: Fact)


sealed interface Fact

data class ExprFact(val expr: Expr): Fact

data class NewArrayTypeFact(val type: Type, val expr: Expr): Fact

data class ActionFact(val prefix: IncDec?, val ident: Ident, val actionOp: ActionOperation?, val suffix: IncDec?):
    Fact

sealed interface ActionOperation

data class ArrayAccessOperation(val expr: Expr): ActionOperation

data class CallOperation(var actParList: List<Expr>): ActionOperation


enum class IncDec {
    INCREASE,
    DECREASE
}
