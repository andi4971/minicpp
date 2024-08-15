package org.azauner.ast.node

data class Block(val entries: List<BlockEntry>, val scope: Scope)


sealed interface BlockEntry
