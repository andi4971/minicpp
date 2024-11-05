package org.azauner.minicpp.ast.node

import org.azauner.minicpp.ast.node.scope.Variable

data class ConstDefEntry(
    val ident: Ident,
    val value: Init,
    val variable: Variable
)
