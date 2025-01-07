package org.azauner.minicpp.ast.node


data class VarDef(
    val type: ExprType,
    val entries: List<VarDefEntry>
) : BlockEntry, MiniCppEntry
