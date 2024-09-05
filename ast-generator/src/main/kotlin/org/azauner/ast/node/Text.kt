package org.azauner.ast.node

@JvmInline
value class Text(private val text: String): OutputStatEntry {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append(text)
    }
}
