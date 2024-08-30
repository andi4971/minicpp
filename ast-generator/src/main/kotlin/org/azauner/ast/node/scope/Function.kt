package org.azauner.ast.node.scope

import org.azauner.ast.node.ExprType
import org.azauner.ast.node.Ident

data class Function(val ident: Ident, val returnType: ExprType, val formParTypes: List<ExprType>)
