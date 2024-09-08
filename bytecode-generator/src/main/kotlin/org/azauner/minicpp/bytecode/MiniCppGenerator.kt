package org.azauner.minicpp.bytecode
import org.azauner.minicpp.ast.node.MiniCpp
import org.objectweb.asm.tree.ClassNode

class MiniCppGenerator(private val miniCpp: MiniCpp) {
    fun generateClassNode(): ClassNode {
        return ClassNode().run {
            name = miniCpp.fileName.also {
                val regex = Regex("^[a-zA-Z_][a-zA-Z\\d_]*\$")
                require(regex.matches(it)) { "Invalid class name: $it" }
            }
            miniCpp.entries.forEach { entry ->
                /*when (entry) {
                    is FieldNodeProvider -> entry.getFieldNode(true).let { fields.addAll(it) }
                    is FuncDef -> entry.getMethodNode().let { methods.add(it)}
                    else ->  ""
                }*/
            }

            this
        }
    }
}
