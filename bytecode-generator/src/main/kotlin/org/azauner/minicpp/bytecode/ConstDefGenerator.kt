package org.azauner.minicpp.bytecode

import org.azauner.minicpp.ast.node.ConstDef
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldNode

class ConstDefGenerator(private val constDef: ConstDef) {

    fun getFieldNode(isStatic: Boolean): List<FieldNode> {
        return constDef.entries.map { entry ->
            FieldNode(
                Opcodes.ACC_PUBLIC
                        or( if(isStatic) Opcodes.ACC_STATIC else 0)
                        or Opcodes.ACC_FINAL,
                entry.ident.name,
                constDef.type.descriptor,
                null,
                entry.value.value.getValue()
            )
        }
    }
}
