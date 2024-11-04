package org.azauner.minicpp.ast.node


sealed interface Fact

data class ExprFact(val expr: org.azauner.minicpp.ast.node.Expr) : org.azauner.minicpp.ast.node.Fact

data class NewArrayTypeFact(
    val type: org.azauner.minicpp.ast.node.ExprType,
    val expr: org.azauner.minicpp.ast.node.Expr
) :
    org.azauner.minicpp.ast.node.Fact

data class ActionFact(
    val prefix: org.azauner.minicpp.ast.node.IncDec?,
    val ident: org.azauner.minicpp.ast.node.Ident,
    val actionOp: org.azauner.minicpp.ast.node.ActionOperation?,
    val suffix: org.azauner.minicpp.ast.node.IncDec?,
    val scope: org.azauner.minicpp.ast.node.scope.Scope
) :
    org.azauner.minicpp.ast.node.Fact
