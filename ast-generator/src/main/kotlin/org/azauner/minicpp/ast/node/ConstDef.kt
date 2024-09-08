package org.azauner.minicpp.ast.node


data class ConstDef(val type: ExprType, val entries: List<ConstDefEntry>) : BlockEntry, MiniCppEntry
