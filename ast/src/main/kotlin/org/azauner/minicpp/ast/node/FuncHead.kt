package org.azauner.minicpp.ast.node

data class FuncHead(
    val type: org.azauner.minicpp.ast.node.ExprType,
    val ident: org.azauner.minicpp.ast.node.Ident,
    val formParList: org.azauner.minicpp.ast.node.FormParList?
)
