package org.azauner.ast.node

import aj.org.objectweb.asm.Opcodes
import org.azauner.ast.SourceCodeGenerator
import org.objectweb.asm.tree.MethodNode

data class FuncDef(val funHead: FuncHead, val block: Block): MiniCppEntry, SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        funHead.generateSourceCode(sb)
        sb.appendLine()
        block.generateSourceCode(sb)
    }

    fun getMethodNode(): MethodNode {
        val methodNode = MethodNode(
            Opcodes.ACC_PUBLIC or Opcodes.ACC_STATIC,
            funHead.ident.name,
            funHead.getDescriptor(),
            null,
            null

        )

        return methodNode
    }
}
