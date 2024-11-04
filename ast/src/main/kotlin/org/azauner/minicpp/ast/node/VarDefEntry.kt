package org.azauner.minicpp.ast.node

import org.azauner.minicpp.ast.node.scope.Variable


data class VarDefEntry(
    val ident: org.azauner.minicpp.ast.node.Ident,
    val pointer: Boolean,
    val value: org.azauner.minicpp.ast.node.Init?,
    val variable: Variable
)
