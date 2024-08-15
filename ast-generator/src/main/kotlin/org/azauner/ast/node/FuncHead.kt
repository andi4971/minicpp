package org.azauner.ast.node

data class FuncHead(val type: Type, val star: Boolean, val ident: Ident, val formParList: FormParList?)
