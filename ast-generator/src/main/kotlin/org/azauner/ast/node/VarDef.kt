package org.azauner.ast.node

//todo check type on validation
data class VarDef(val type: Type, val entries: List<VarDefEntry>): BlockEntry, MiniCppEntry
