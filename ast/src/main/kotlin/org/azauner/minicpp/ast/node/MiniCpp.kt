package org.azauner.minicpp.ast.node

data class MiniCpp(
    val className: String,
    val globalScope: org.azauner.minicpp.ast.node.scope.Scope,
    val entries: List<org.azauner.minicpp.ast.node.MiniCppEntry>
)

sealed interface MiniCppEntry

data object Sem : org.azauner.minicpp.ast.node.MiniCppEntry
