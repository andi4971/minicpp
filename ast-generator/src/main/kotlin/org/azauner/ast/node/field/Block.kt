package org.azauner.ast.node.field

data class Block(val entries: List<BlockEntry>)


sealed interface BlockEntry
