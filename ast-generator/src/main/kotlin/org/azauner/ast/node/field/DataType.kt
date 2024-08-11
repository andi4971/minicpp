package org.azauner.ast.node.field

sealed interface DataType: FactChild

data class IntType(val value: Int): DataType

data class BoolType(val value: Boolean): DataType

data object NullPtrType: DataType
