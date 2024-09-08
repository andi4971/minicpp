package org.azauner.minicpp.sourcecode

import org.azauner.minicpp.ast.node.*

fun MiniCpp.generateSourceCode(): String {
    val sb = StringBuilder()
    entries.forEach {
        when (it) {
            is ConstDef -> it.generateSourceCode(sb)
            is FuncDecl -> it.generateSourceCode(sb)
            is FuncDef -> it.generateSourceCode(sb)
            Sem -> sb.appendLine(";")
            is VarDef -> it.generateSourceCode(sb)
        } }
    return sb.toString()
}
