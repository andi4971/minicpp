package org.azauner.minicpp.sourcecode

import org.azauner.minicpp.ast.node.*

fun Expr.generateSourceCode(sb: StringBuilder) {
    firstExpr.generateSourceCode(sb)
    exprEntries.forEach {
        sb.append(it.assignOperator.sourceCode)
        sb.append(" ")
        it.orExpr.generateSourceCode(sb)
        sb.append(" ")
    }
}

fun OrExpr.generateSourceCode(sb: StringBuilder) {
    andExpressions.first().generateSourceCode(sb)
    andExpressions.drop(1).forEach {
        sb.append(" || ")
        it.generateSourceCode(sb)
    }
}

fun AndExpr. generateSourceCode(sb: StringBuilder) {
    relExpressions.first().generateSourceCode(sb)
    relExpressions.drop(1).forEach {
        sb.append(" && ")
        it.generateSourceCode(sb)
    }
}

fun RelExpr.generateSourceCode(sb: StringBuilder) {
    firstExpr.generateSourceCode(sb)
    relExprEntries.forEach {
        sb.append(" ")
        sb.append(it.relOperator.sourceCode)
        sb.append(" ")
        it.simpleExpr.generateSourceCode(sb)
    }
}

fun SimpleExpr.generateSourceCode(sb: StringBuilder) {
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

fun Term.generateSourceCode(sb: StringBuilder) {
    firstNotFact.generateSourceCode(sb)
    termEntries.forEach {
        sb.append(" ")
        sb.append(it.termOperator.sourceCode)
        sb.append(" ")
        it.notFact.generateSourceCode(sb)
    }
}

fun NotFact.generateSourceCode(sb: StringBuilder) {
    if (negated) {
        sb.append("!")
    }
    fact.generateSourceCode(sb)
}

fun Fact.generateSourceCode(sb: StringBuilder) {
    when (this) {
        is ExprFact -> generateSourceCode(sb)
        is NewArrayTypeFact -> generateSourceCode(sb)
        is ActionFact -> generateSourceCode(sb)
        is DataType -> generateSourceCode(sb)
    }
}

fun DataType.generateSourceCode(sb: StringBuilder) {
    when(this) {
        NullPtrType -> sb.append("nullptr")
        else -> sb.append(this.getValue()!!.toString())
    }
}

fun ExprFact.generateSourceCode(sb: StringBuilder) {
    sb.append("(")
    expr.generateSourceCode(sb)
    sb.append(")")
}

fun NewArrayTypeFact.generateSourceCode(sb: StringBuilder) {
    sb.append("new ")
    type.generateSourceCode(sb)
    sb.append("[")
    expr.generateSourceCode(sb)
    sb.append("]")
}

fun ActionFact.generateSourceCode(sb: StringBuilder) {
    prefix?.let {
        sb.append(it.sourceCode)
    }
    sb.append(ident.name)
    actionOp?.generateSourceCode(sb)
    suffix?.let {
        sb.append(it.sourceCode)
    }
}

fun ActionOperation.generateSourceCode(sb: StringBuilder) {
    when (this) {
        is ArrayAccessOperation -> generateSourceCode(sb)
        is CallOperation -> generateSourceCode(sb)
    }
}

fun ArrayAccessOperation.generateSourceCode(sb: StringBuilder) {
    sb.append("[")
    expr.generateSourceCode(sb)
    sb.append("]")
}

fun CallOperation.generateSourceCode(sb: StringBuilder) {
    sb.append("(")
    actParList.forEachIndexed { index, expr ->
        expr.generateSourceCode(sb)
        if (index != actParList.lastIndex) {
            sb.append(", ")
        }
    }
    sb.append(")")
}
