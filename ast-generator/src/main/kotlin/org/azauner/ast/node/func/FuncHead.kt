package org.azauner.ast.node.func

import org.azauner.ast.node.Type

data class FuncHead(val type: Type, val star: Boolean, val ident: String, val formParList: FormParList?)
