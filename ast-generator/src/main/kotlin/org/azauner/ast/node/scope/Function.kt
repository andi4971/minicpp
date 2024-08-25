package org.azauner.ast.node.scope

import org.azauner.ast.node.FormParList
import org.azauner.ast.node.Ident
import org.azauner.ast.node.Type

data class Function(val ident: Ident, val returnType: Type, val returnTypePointer: Boolean, val formParList: FormParList?)
