package org.azauner.ast.node

import org.azauner.ast.bytecode.InstructionListProvider
import org.azauner.ast.node.scope.Scope
import org.objectweb.asm.tree.InsnList

data class Block(val entries: List<BlockEntry>, val scope: Scope)  {


    fun getInstructions(): InsnList {
        val instructions = InsnList()
        return instructions
    }
}


sealed interface BlockEntry: InstructionListProvider
