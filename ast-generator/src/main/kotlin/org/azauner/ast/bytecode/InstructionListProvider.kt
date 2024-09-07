package org.azauner.ast.bytecode

import org.azauner.ast.node.Ident
import org.objectweb.asm.tree.InsnList

interface InstructionListProvider {

    fun getInstructions(spix: HashMap<Ident, Int>): InsnList
}
