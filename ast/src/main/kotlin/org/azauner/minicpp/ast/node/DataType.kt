package org.azauner.minicpp.ast.node

sealed interface DataType : org.azauner.minicpp.ast.node.Fact {
    fun getValue(): Any?
}

data class IntType(val value: Int) : org.azauner.minicpp.ast.node.DataType {
    override fun getValue(): Any {
        return value
    }
}

data class BoolType(val value: Boolean) : org.azauner.minicpp.ast.node.DataType {
    override fun getValue(): Any {
        return value
    }
}

data object NullPtrType : org.azauner.minicpp.ast.node.DataType {
    override fun getValue(): Any? {
        return null
    }
}
