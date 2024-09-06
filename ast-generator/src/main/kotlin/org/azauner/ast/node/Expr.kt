package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class Expr(val firstExpr: OrExpr, val exprEntries: List<ExprEntry>) : OutputStatEntry, SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        firstExpr.generateSourceCode(sb)
        exprEntries.forEach {
            sb.append(it.assignOperator.sourceCode)
            sb.append(" ")
            it.orExpr.generateSourceCode(sb)
            sb.append(" ")
        }
    }
}

data class ExprEntry(val orExpr: OrExpr, val assignOperator: AssignOperator)

enum class AssignOperator(val sourceCode: String) {
    ASSIGN ("="),
    ADD_ASSIGN("+="),
    SUB_ASSIGN("-="),
    MUL_ASSIGN("*="),
    DIV_ASSIGN("/="),
    MOD_ASSIGN("%=")
}

