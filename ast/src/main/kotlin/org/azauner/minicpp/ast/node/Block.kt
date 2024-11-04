package org.azauner.minicpp.ast.node

data class Block(
    val entries: List<org.azauner.minicpp.ast.node.BlockEntry>,
    val scope: org.azauner.minicpp.ast.node.scope.Scope
)

sealed interface BlockEntry
