package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator
import org.azauner.ast.node.scope.Scope

data class Block(val entries: List<BlockEntry>, val scope: Scope) : SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.appendLine("{")
        entries.forEach {
            it.generateSourceCode(sb)
        }
        sb.appendLine("}")
    }
}


sealed interface BlockEntry: SourceCodeGenerator
