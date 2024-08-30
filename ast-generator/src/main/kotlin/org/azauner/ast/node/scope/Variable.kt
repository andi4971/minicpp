package org.azauner.ast.node.scope

import org.azauner.ast.node.ExprType
import org.azauner.ast.node.Ident

class Variable(val ident: Ident, val type: ExprType, val const: Boolean) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Variable

        if (ident != other.ident) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ident.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }

}
