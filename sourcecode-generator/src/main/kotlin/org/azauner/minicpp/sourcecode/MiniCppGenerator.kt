package org.azauner.minicpp.sourcecode

fun org.azauner.minicpp.ast.node.MiniCpp.generateSourceCode(): String {
    val sb = StringBuilder()
    entries.forEach {
        when (it) {
            is org.azauner.minicpp.ast.node.ConstDef -> it.generateSourceCode(sb)
            is org.azauner.minicpp.ast.node.FuncDecl -> it.generateSourceCode(sb)
            is org.azauner.minicpp.ast.node.FuncDef -> it.generateSourceCode(sb)
            org.azauner.minicpp.ast.node.Sem -> sb.appendLine(";")
            is org.azauner.minicpp.ast.node.VarDef -> it.generateSourceCode(sb)
        } }
    return sb.toString()
}
