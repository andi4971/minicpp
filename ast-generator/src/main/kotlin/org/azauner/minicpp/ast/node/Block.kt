package org.azauner.minicpp.ast.node

import org.azauner.minicpp.ast.node.scope.Scope

data class Block(val entries: List<BlockEntry>, val scope: Scope)

sealed interface BlockEntry
