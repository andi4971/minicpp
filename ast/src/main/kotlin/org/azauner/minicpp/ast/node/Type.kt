package org.azauner.minicpp.ast.node


enum class ExprType(val descriptor: String = "") {
    VOID("V"),
    BOOL("Z"),
    INT("I"),
    NULLPTR,
    //used when an index operation on the array is performed
    INT_ARR("[I"),
    BOOL_ARR("[Z"),

    INT_PTR,
    BOOL_PTR;

    /*//used when the array as a whole is used
    BOOL_ARR_PTR,
    INT_ARR_PTR*/
}

val ARR_TYPES = setOf(
    org.azauner.minicpp.ast.node.ExprType.BOOL_ARR, org.azauner.minicpp.ast.node.ExprType.INT_ARR,
    /*ExprType.BOOL_ARR_PTR, ExprType.INT_ARR_PTR*/
)
val INIT_TYPES_NOT_NULL = setOf(org.azauner.minicpp.ast.node.ExprType.INT, org.azauner.minicpp.ast.node.ExprType.BOOL)

