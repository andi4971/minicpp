package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class FuncDef(val funHead: FuncHead, val block: Block): MiniCppEntry, SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        funHead.generateSourceCode(sb)
        sb.appendLine()
        block.generateSourceCode(sb)
    }
}
