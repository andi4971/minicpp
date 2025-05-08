package org.azauner.minicpp.ast.node.scope

import org.azauner.minicpp.ast.node.ExprType
import org.azauner.minicpp.ast.node.Ident

data class Function(
    val ident: Ident,
    val returnType: ExprType,
    val formParTypes: List<ExprType>,
    var isDefined: Boolean = false
)
