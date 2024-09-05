package org.azauner.ast.node

sealed interface DataType: Fact

data class IntType(val value: Int): DataType {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append(value)
    }
}

data class BoolType(val value: Boolean): DataType {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append(value)
    }
}

data object NullPtrType: DataType {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("nullptr")
    }
}
