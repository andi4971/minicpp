package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class AndExpr(val relExpressions: List<RelExpr>): SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        relExpressions.first().generateSourceCode(sb)
        relExpressions.drop(1).forEach {
            sb.append(" && ")
            it.generateSourceCode(sb)
        }
    }
}
