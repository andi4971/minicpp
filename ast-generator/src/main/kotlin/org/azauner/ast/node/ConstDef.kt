package org.azauner.ast.node

data class ConstDef(val type: Type, val entries: List<ConstDefEntry>) : BlockEntry, MiniCppEntry
