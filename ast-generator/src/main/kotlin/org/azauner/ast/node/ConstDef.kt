package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class ConstDef(val type: ExprType, val entries: List<ConstDefEntry>) : BlockEntry, MiniCppEntry, SourceCodeGenerator {

    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("const ")
        type.generateSourceCode(sb)
        sb.append(" ")
        entries.forEachIndexed { index, entry ->
            entry.generateSourceCode(sb)
            if (index != entries.lastIndex) {
                sb.append(", ")
            }
        }
        sb.appendLine(";")
    }
}
