package org.azauner.ast.node

data object Endl: OutputStatEntry {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("endl")
    }
}
