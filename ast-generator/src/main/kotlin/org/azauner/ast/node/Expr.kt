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


data class OrExpr(val andExpressions: List<AndExpr>): SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        andExpressions.first().generateSourceCode(sb)
        andExpressions.drop(1).forEach {
            sb.append(" || ")
            it.generateSourceCode(sb)
        }
    }
}

data class AndExpr(val relExpressions: List<RelExpr>): SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        relExpressions.first().generateSourceCode(sb)
        relExpressions.drop(1).forEach {
            sb.append(" && ")
            it.generateSourceCode(sb)
        }
    }
}

data class RelExpr(val firstExpr: SimpleExpr, val relExprEntries: List<RelExprEntry>): SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        firstExpr.generateSourceCode(sb)
        relExprEntries.forEach {
            sb.append(" ")
            sb.append(it.relOperator.sourceCode)
            sb.append(" ")
            it.simpleExpr.generateSourceCode(sb)
        }
    }
}

data class RelExprEntry(val simpleExpr: SimpleExpr, val relOperator: RelOperator)

enum class RelOperator(val sourceCode: String) {
    EQUAL("=="),
    NOT_EQUAL("!="),
    LESS_THAN_EQUAL("<="),
    LESS_THAN("<"),
    GREATER_THAN_EQUAL(">="),
    GREATER_THAN(">")
}

val boolRelOperators = listOf(RelOperator.EQUAL, RelOperator.NOT_EQUAL)

data class SimpleExpr(val sign: Sign?, val term: Term, val simpleExprEntries: List<SimpleExprEntry>): SourceCodeGenerator {
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

data class NotFact(val negated: Boolean, val fact: Fact) : SourceCodeGenerator {
    override fun generateSourceCode(sb: StringBuilder) {
        if (negated) {
            sb.append("!")
        }
        fact.generateSourceCode(sb)
    }
}


sealed interface Fact : SourceCodeGenerator

data class ExprFact(val expr: Expr): Fact {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("(")
        expr.generateSourceCode(sb)
        sb.append(")")
    }
}

data class NewArrayTypeFact(val type: ExprType, val expr: Expr): Fact {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("new ")
        type.generateSourceCode(sb)
        sb.append("[")
        expr.generateSourceCode(sb)
        sb.append("]")
    }
}

data class ActionFact(val prefix: IncDec?, val ident: Ident, val actionOp: ActionOperation?, val suffix: IncDec?):
    Fact {
    override fun generateSourceCode(sb: StringBuilder) {
        prefix?.let {
            sb.append(it.sourceCode)
        }
        sb.append(ident.name)
        actionOp?.generateSourceCode(sb)
        suffix?.let {
            sb.append(it.sourceCode)
        }
    }
}

sealed interface ActionOperation: SourceCodeGenerator

data class ArrayAccessOperation(val expr: Expr): ActionOperation {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("[")
        expr.generateSourceCode(sb)
        sb.append("]")
    }
}

data class CallOperation(var actParList: List<Expr>): ActionOperation {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("(")
        actParList.forEachIndexed { index, expr ->
            expr.generateSourceCode(sb)
            if (index != actParList.lastIndex) {
                sb.append(", ")
            }
        }
        sb.append(")")
    }
}


enum class IncDec(val sourceCode: String) {
    INCREASE("++"),
    DECREASE("--")
}

