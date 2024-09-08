package org.azauner.ast.node


sealed interface Fact

data class ExprFact(val expr: Expr): Fact

data class NewArrayTypeFact(val type: ExprType, val expr: Expr): Fact

data class ActionFact(val prefix: IncDec?, val ident: Ident, val actionOp: ActionOperation?, val suffix: IncDec?): Fact
