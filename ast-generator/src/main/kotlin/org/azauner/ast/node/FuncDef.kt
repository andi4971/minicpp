package org.azauner.ast.node

import aj.org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode

data class FuncDef(val funHead: FuncHead, val block: Block): MiniCppEntry  {


    fun getMethodNode(): MethodNode {
        val methodNode = MethodNode(
            Opcodes.ACC_PUBLIC or Opcodes.ACC_STATIC,
            funHead.ident.name,
            funHead.getDescriptor(),
            null,
            null
        )
        //add instructions to methodNode
        methodNode.instructions.add(block.getInstructions())

        return methodNode
    }
}
