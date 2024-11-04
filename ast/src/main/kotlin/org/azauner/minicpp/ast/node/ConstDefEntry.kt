package org.azauner.minicpp.ast.node

import org.azauner.minicpp.ast.node.scope.Variable

data class ConstDefEntry(
    val ident: org.azauner.minicpp.ast.node.Ident,
    val value: org.azauner.minicpp.ast.node.Init,
    val variable: Variable
)
