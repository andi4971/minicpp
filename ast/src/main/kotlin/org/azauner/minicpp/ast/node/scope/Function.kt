package org.azauner.minicpp.ast.node.scope

data class Function(
    val ident: org.azauner.minicpp.ast.node.Ident,
    val returnType: org.azauner.minicpp.ast.node.ExprType,
    val formParTypes: List<org.azauner.minicpp.ast.node.ExprType>,
    var isDefined: Boolean = false
)
