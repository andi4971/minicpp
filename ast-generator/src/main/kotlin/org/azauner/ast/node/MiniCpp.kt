package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator
import org.azauner.ast.bytecode.FieldNodeProvider
import org.azauner.ast.node.scope.Scope
import org.objectweb.asm.tree.ClassNode

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

    fun generateClassNode(): ClassNode {
       return ClassNode().run {
         name = fileName.also {
             val regex = Regex("^[a-zA-Z_][a-zA-Z\\d_]*\$")
                require(regex.matches(it)) { "Invalid class name: $it" }
         }
            entries.forEach { entry ->
                when (entry) {
                    is FieldNodeProvider -> entry.getFieldNode(true).let { fields.addAll(it) }
                    is FuncDef -> entry.getMethodNode().let { methods.add(it)}
                    else ->  ""
                }
            }

           this
       }
    }
}

sealed interface MiniCppEntry

data object Sem: MiniCppEntry
