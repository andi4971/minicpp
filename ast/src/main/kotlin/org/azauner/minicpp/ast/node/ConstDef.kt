package org.azauner.minicpp.ast.node


data class ConstDef(
    val type: org.azauner.minicpp.ast.node.ExprType,
    val entries: List<org.azauner.minicpp.ast.node.ConstDefEntry>
) : org.azauner.minicpp.ast.node.BlockEntry,
    org.azauner.minicpp.ast.node.MiniCppEntry
