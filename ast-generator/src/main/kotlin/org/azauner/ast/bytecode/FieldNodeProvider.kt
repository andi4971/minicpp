package org.azauner.ast.bytecode

import org.objectweb.asm.tree.FieldNode

interface FieldNodeProvider {
    fun getFieldNode(isStatic: Boolean): List<FieldNode>
}
