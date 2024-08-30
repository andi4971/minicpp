package org.azauner.ast.node

enum class ExprType {
    VOID,
    BOOL,
    INT,
    NULLPTR,
    //used when an index operation on the array is performed
    INT_ARR,
    BOOL_ARR,

    INT_PTR,
    BOOL_PTR,
    /*//used when the array as a whole is used
    BOOL_ARR_PTR,
    INT_ARR_PTR*/
}

val ARR_TYPES = setOf(ExprType.BOOL_ARR, ExprType.INT_ARR,
    /*ExprType.BOOL_ARR_PTR, ExprType.INT_ARR_PTR*/
)
val INIT_TYPES_NOT_NULL = setOf(ExprType.INT, ExprType.BOOL)

