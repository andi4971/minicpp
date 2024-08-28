package org.azauner.ast.node.scope

import org.azauner.ast.node.ExprType
import org.azauner.ast.node.Ident
import org.azauner.ast.node.Type

data class Function(val ident: Ident, val returnType: Type, val returnTypePointer: Boolean, val formParTypes: List<ExprType>)
