package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class OrExpr(val andExpressions: List<AndExpr>): SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        andExpressions.first().generateSourceCode(sb)
        andExpressions.drop(1).forEach {
            sb.append(" || ")
            it.generateSourceCode(sb)
        }
    }
}
