package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class Init(val value: DataType): SourceCodeGenerator {

    override fun generateSourceCode(sb: StringBuilder) {
        value.generateSourceCode(sb)
    }

}
