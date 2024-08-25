package org.azauner.ast.node.scope

import org.azauner.ast.node.Ident
import org.azauner.ast.node.Type

class Variable(val ident: Ident, val type: Type, val pointer: Boolean, val const: Boolean) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Variable

        if (ident != other.ident) return false
        if (type != other.type) return false
        if (pointer != other.pointer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ident.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + pointer.hashCode()
        return result
    }

}
