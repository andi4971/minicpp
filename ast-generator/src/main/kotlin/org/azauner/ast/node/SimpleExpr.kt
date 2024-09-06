package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class SimpleExpr(val sign: Sign?, val term: Term, val simpleExprEntries: List<SimpleExprEntry>):
    SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        sign?.let {
            sb.append(it.sourceCode)
        }
        term.generateSourceCode(sb)
        simpleExprEntries.forEach {
            sb.append(" ")
            sb.append(it.sign.sourceCode)
            sb.append(" ")
            it.term.generateSourceCode(sb)
        }
    }

}

enum class Sign(val sourceCode: String) {
    PLUS("+"),
    MINUS("-")
}

data class SimpleExprEntry(val sign: Sign, val term: Term)
