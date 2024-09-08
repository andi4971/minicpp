package org.azauner.minicpp.bytecode

import org.azauner.minicpp.ast.node.VarDef
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldNode

class VarDefGenerator(private val varDef: VarDef) {
    fun getFieldNode(isStatic: Boolean): List<FieldNode> {
        return varDef.entries.map { entry ->
            FieldNode(
                Opcodes.ACC_PUBLIC
                        or (if(isStatic) Opcodes.ACC_STATIC else 0),
                entry.ident.name,
                varDef.type.descriptor,
                null,
                entry.value?.value?.getValue()
            )
        }
    }

}
