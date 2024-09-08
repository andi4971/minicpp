package org.azauner.minicpp.bytecode

import org.azauner.minicpp.ast.node.ConstDef
import org.azauner.minicpp.ast.node.MiniCpp
import org.azauner.minicpp.ast.node.VarDef
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes.ACC_PUBLIC
import org.objectweb.asm.tree.ClassNode

class MiniCppGenerator(private val miniCpp: MiniCpp) {

    companion object {
        private const val CLASS_FILE_VERSION = 65
    }

    fun generateClassNode(): ClassNode {
        return ClassNode().run {
            name = miniCpp.className.also {
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

    fun generateByteCode(): ByteArray {
        val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES or ClassWriter.COMPUTE_MAXS)

        classWriter.visit(
            CLASS_FILE_VERSION,
            ACC_PUBLIC,
            miniCpp.className,
            null,
            "java/lang/Object",
            null
        )

        val staticVarDefGenerator = StaticVarDefGenerator(classWriter, miniCpp.className)
        miniCpp.entries.forEach {
            when (it) {
                is VarDef -> staticVarDefGenerator.generateStatic(it)
                is ConstDef -> ConstDefGenerator(classWriter).generateStatic(it)
                else -> ""
            }
        }
        staticVarDefGenerator.generateStaticInitBlock()

        classWriter.visitEnd()


        return classWriter.toByteArray()
    }
}
