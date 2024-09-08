package org.azauner.ast.node

sealed interface DataType: Fact {
    fun getValue(): Any?
}

data class IntType(val value: Int): DataType {
    override fun getValue(): Any {
        return value
    }
}

data class BoolType(val value: Boolean): DataType {
    override fun getValue(): Any {
        return value
    }
}

data object NullPtrType: DataType {
    override fun getValue(): Any? {
        return null
    }
}
