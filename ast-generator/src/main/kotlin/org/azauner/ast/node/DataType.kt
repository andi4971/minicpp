package org.azauner.ast.node

sealed interface DataType: Fact

data class IntType(val value: Int): DataType

data class BoolType(val value: Boolean): DataType

data object NullPtrType: DataType
