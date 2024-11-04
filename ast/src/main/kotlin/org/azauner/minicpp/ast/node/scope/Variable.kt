package org.azauner.minicpp.ast.node.scope

class Variable(
    val ident: org.azauner.minicpp.ast.node.Ident,
    val type: org.azauner.minicpp.ast.node.ExprType,
    val const: Boolean,
    val static: Boolean,
    var index: Int,
    val constValue: Any? = null
) {
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
