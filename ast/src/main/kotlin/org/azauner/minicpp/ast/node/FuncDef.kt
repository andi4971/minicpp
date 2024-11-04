package org.azauner.minicpp.ast.node


data class FuncDef(val funHead: org.azauner.minicpp.ast.node.FuncHead, val block: org.azauner.minicpp.ast.node.Block) :
    org.azauner.minicpp.ast.node.MiniCppEntry
