package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class VarDef(val type: ExprType, val entries: List<VarDefEntry>): BlockEntry, MiniCppEntry, SourceCodeGenerator {

    override fun generateSourceCode(sb: StringBuilder) {
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
