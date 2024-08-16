package org.azauner.ast.node

data class MiniCpp(
    val globalScope: Scope,
    val entries: List<MiniCppEntry>
)

sealed interface MiniCppEntry

data object Sem: MiniCppEntry
