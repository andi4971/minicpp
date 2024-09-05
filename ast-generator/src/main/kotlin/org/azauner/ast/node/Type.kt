package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

enum class ExprType: SourceCodeGenerator {
    VOID,
    BOOL,
    INT,
    NULLPTR,
    //used when an index operation on the array is performed
    INT_ARR,
    BOOL_ARR,

    INT_PTR,
    BOOL_PTR;

    override fun generateSourceCode(sb: StringBuilder) {
        when (this) {
            VOID -> sb.append("void")
            BOOL -> sb.append("bool")
            INT -> sb.append("int")
            NULLPTR -> sb.append("nullptr")
            INT_ARR -> sb.append("int[]")
            BOOL_ARR -> sb.append("bool[]")
            INT_PTR -> sb.append("int*")
            BOOL_PTR -> sb.append("bool*")
        }
    }

    /*//used when the array as a whole is used
    BOOL_ARR_PTR,
    INT_ARR_PTR*/
}

val ARR_TYPES = setOf(ExprType.BOOL_ARR, ExprType.INT_ARR,
    /*ExprType.BOOL_ARR_PTR, ExprType.INT_ARR_PTR*/
)
val INIT_TYPES_NOT_NULL = setOf(ExprType.INT, ExprType.BOOL)

