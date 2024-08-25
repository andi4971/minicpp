package org.azauner.ast.node

import org.azauner.ast.node.scope.Scope

data class MiniCpp(
    val globalScope: Scope,
    val entries: List<MiniCppEntry>
)

sealed interface MiniCppEntry

data object Sem: MiniCppEntry
