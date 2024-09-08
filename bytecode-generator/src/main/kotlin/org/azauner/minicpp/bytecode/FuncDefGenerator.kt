package org.azauner.minicpp.bytecode

import org.azauner.minicpp.ast.node.FuncDef
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode

class FuncDefGenerator(private val funcDef: FuncDef) {

    fun getMethodNode(): MethodNode {
        val methodNode = MethodNode(
            Opcodes.ACC_PUBLIC or Opcodes.ACC_STATIC,
            funcDef.funHead.ident.name,
            funcDef.funHead.getDescriptor(),
            null,
            null
        )
        //add instructions to methodNode
/*
        methodNode.instructions.add(funcDef.block.getInstructions())
*/

        return methodNode
    }
}
