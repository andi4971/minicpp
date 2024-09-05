package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator
import org.azauner.ast.node.scope.Scope

data class MiniCpp(
    val fileName: String,
    val globalScope: Scope,
    val entries: List<MiniCppEntry>
) {

    fun generateSourceCode(): String {
        val sb = StringBuilder()
        entries.forEach {
            when (it) {
            is SourceCodeGenerator -> it.generateSourceCode(sb)
            else -> sb.appendLine(";")
        } }
        return sb.toString()
    }
}

sealed interface MiniCppEntry

data object Sem: MiniCppEntry
