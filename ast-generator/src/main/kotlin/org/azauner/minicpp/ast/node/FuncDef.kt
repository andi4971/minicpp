package org.azauner.minicpp.ast.node


data class FuncDef(val funHead: FuncHead, val block: Block): MiniCppEntry
