package org.azauner.minicpp.ast.node


data class OrExpr(
    val andExpressions: List<org.azauner.minicpp.ast.node.AndExpr>,
    val scope: org.azauner.minicpp.ast.node.scope.Scope
)
