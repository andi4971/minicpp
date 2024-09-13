package org.azauner.minicpp.ast.node

import org.azauner.minicpp.ast.node.scope.Scope


sealed interface Fact

data class ExprFact(val expr: Expr): Fact

data class NewArrayTypeFact(val type: ExprType, val expr: Expr): Fact

data class ActionFact(val prefix: IncDec?, val ident: Ident, val actionOp: ActionOperation?, val suffix: IncDec?, val scope: Scope): Fact
