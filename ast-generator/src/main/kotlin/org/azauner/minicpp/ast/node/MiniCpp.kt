package org.azauner.minicpp.ast.node

import org.azauner.minicpp.ast.node.scope.Scope

data class MiniCpp(
    val fileName: String,
    val globalScope: Scope,
    val entries: List<MiniCppEntry>
)

sealed interface MiniCppEntry

data object Sem: MiniCppEntry
