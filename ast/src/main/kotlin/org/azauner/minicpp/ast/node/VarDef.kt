package org.azauner.minicpp.ast.node


data class VarDef(
    val type: org.azauner.minicpp.ast.node.ExprType,
    val entries: List<org.azauner.minicpp.ast.node.VarDefEntry>
) : org.azauner.minicpp.ast.node.BlockEntry,
    org.azauner.minicpp.ast.node.MiniCppEntry
