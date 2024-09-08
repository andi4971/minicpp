package org.azauner.minicpp.sourcecode

import org.azauner.minicpp.ast.node.ExprType
import org.azauner.minicpp.ast.node.ExprType.*

fun ExprType.generateSourceCode(sb: StringBuilder) {
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
