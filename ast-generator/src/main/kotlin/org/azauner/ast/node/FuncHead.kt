package org.azauner.ast.node

data class FuncHead(val type: ExprType, val ident: Ident, val formParList: FormParList?)
