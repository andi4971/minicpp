package org.azauner.minicpp.sourcecode

import org.azauner.minicpp.ast.node.ExprType.*

fun org.azauner.minicpp.ast.node.ExprType.generateSourceCode(sb: StringBuilder) {
    when (this) {
        VOID -> sb.append("void")
        BOOL -> sb.append("bool")
        INT -> sb.append("int")
        NULLPTR -> sb.append("nullptr")
        INT_ARR -> sb.append("int")
        BOOL_ARR -> sb.append("bool")
    }
}
