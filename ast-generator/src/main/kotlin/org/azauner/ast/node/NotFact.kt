package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class NotFact(val negated: Boolean, val fact: Fact) : SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        if (negated) {
            sb.append("!")
        }
        fact.generateSourceCode(sb)
    }
}
