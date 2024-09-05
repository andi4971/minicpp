package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class VarDefEntry(val ident: Ident, val pointer: Boolean, val value: Init?): SourceCodeGenerator {

    override fun generateSourceCode(sb: StringBuilder) {
        if (pointer) {
            sb.append("*")
        }
        sb.append("${ident.name} ")
        value?.let {
            sb.append(" = ")
            it.generateSourceCode(sb)
        }
    }
}
