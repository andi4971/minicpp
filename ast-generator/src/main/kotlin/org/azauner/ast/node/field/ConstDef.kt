package org.azauner.ast.node.field

import org.azauner.ast.node.Type

data class ConstDef(val type: Type, val entries: List<ConstDefEntry>) : BlockEntry()
