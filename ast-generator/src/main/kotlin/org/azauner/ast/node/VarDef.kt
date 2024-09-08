package org.azauner.ast.node

import aj.org.objectweb.asm.Opcodes
import org.azauner.ast.bytecode.FieldNodeProvider
import org.objectweb.asm.tree.FieldNode

data class VarDef(val type: ExprType, val entries: List<VarDefEntry>): BlockEntry, MiniCppEntry,
    FieldNodeProvider {

    override fun getFieldNode(isStatic: Boolean): List<FieldNode> {
        return entries.map { entry ->
            FieldNode(
                Opcodes.ACC_PUBLIC
                        or (if(isStatic) Opcodes.ACC_STATIC else 0),
                entry.ident.name,
                type.descriptor,
                null,
                entry.value?.value?.getValue()
            )
        }
    }


}
