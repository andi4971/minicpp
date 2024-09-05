package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class FuncDecl(val funcHead: FuncHead): MiniCppEntry, SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        funcHead.generateSourceCode(sb)
        sb.appendLine(";")
        sb.appendLine()
    }

}
