package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class Term(val firstNotFact: NotFact, val termEntries: List<TermEntry>) : SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        firstNotFact.generateSourceCode(sb)
        termEntries.forEach {
            sb.append(" ")
            sb.append(it.termOperator.sourceCode)
            sb.append(" ")
            it.notFact.generateSourceCode(sb)
        }
    }
}

data class TermEntry(val notFact: NotFact, val termOperator: TermOperator)

enum class TermOperator(val sourceCode: String) {
    MUL("*"),
    DIV("/"),
    MOD("%")
}
