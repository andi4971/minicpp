package org.azauner.minicpp.ast.node.scope

import org.azauner.minicpp.ast.node.ExprType
import org.azauner.minicpp.ast.node.Ident

class Variable(val ident: Ident, val type: ExprType, val const: Boolean, val static: Boolean, var index: Int) {
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
