package org.azauner.ast.type

sealed class DataType(val type: PrimitiveType)

data class IntType(val value: Int): DataType(PrimitiveType.INT)

data class BoolType(val value: Boolean): DataType(PrimitiveType.BOOL)

class NullPtrType: DataType(PrimitiveType.NULLPTR)

enum class PrimitiveType {
    INT,
    BOOL,
    NULLPTR
}
