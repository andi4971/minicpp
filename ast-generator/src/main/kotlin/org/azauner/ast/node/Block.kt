package org.azauner.ast.node

import org.azauner.ast.node.scope.Scope

data class Block(val entries: List<BlockEntry>, val scope: Scope)


sealed interface BlockEntry
