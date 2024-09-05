package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class ConstDefEntry(val ident: Ident, val value: Init) : SourceCodeGenerator {

    override fun generateSourceCode(sb: StringBuilder) {
            sb.append("${ident.name} = ")
            value.generateSourceCode(sb)
    }

}
