package org.azauner.minicpp.sourcecode

fun org.azauner.minicpp.ast.node.Expr.generateSourceCode(sb: StringBuilder) {
    firstExpr.generateSourceCode(sb)
    exprEntries.forEach {
        sb.append(it.assignOperator.sourceCode)
        sb.append(" ")
        it.orExpr.generateSourceCode(sb)
        sb.append(" ")
    }
}

fun org.azauner.minicpp.ast.node.OrExpr.generateSourceCode(sb: StringBuilder) {
    andExpressions.first().generateSourceCode(sb)
    andExpressions.drop(1).forEach {
        sb.append(" || ")
        it.generateSourceCode(sb)
    }
}

fun org.azauner.minicpp.ast.node.AndExpr.generateSourceCode(sb: StringBuilder) {
    relExpressions.first().generateSourceCode(sb)
    relExpressions.drop(1).forEach {
        sb.append(" && ")
        it.generateSourceCode(sb)
    }
}

fun org.azauner.minicpp.ast.node.RelExpr.generateSourceCode(sb: StringBuilder) {
    firstExpr.generateSourceCode(sb)
    relExprEntries.forEach {
        sb.append(" ")
        sb.append(it.relOperator.sourceCode)
        sb.append(" ")
        it.simpleExpr.generateSourceCode(sb)
    }
}

fun org.azauner.minicpp.ast.node.SimpleExpr.generateSourceCode(sb: StringBuilder) {
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

fun org.azauner.minicpp.ast.node.Term.generateSourceCode(sb: StringBuilder) {
    firstNotFact.generateSourceCode(sb)
    termEntries.forEach {
        sb.append(" ")
        sb.append(it.termOperator.sourceCode)
        sb.append(" ")
        it.notFact.generateSourceCode(sb)
    }
}

fun org.azauner.minicpp.ast.node.NotFact.generateSourceCode(sb: StringBuilder) {
    if (negated) {
        sb.append("!")
    }
    fact.generateSourceCode(sb)
}

fun org.azauner.minicpp.ast.node.Fact.generateSourceCode(sb: StringBuilder) {
    when (this) {
        is org.azauner.minicpp.ast.node.ExprFact -> generateSourceCode(sb)
        is org.azauner.minicpp.ast.node.NewArrayTypeFact -> generateSourceCode(sb)
        is org.azauner.minicpp.ast.node.ActionFact -> generateSourceCode(sb)
        is org.azauner.minicpp.ast.node.DataType -> generateSourceCode(sb)
    }
}

fun org.azauner.minicpp.ast.node.DataType.generateSourceCode(sb: StringBuilder) {
    when(this) {
        org.azauner.minicpp.ast.node.NullPtrType -> sb.append("nullptr")
        else -> sb.append(this.getValue()!!.toString())
    }
}

fun org.azauner.minicpp.ast.node.ExprFact.generateSourceCode(sb: StringBuilder) {
    sb.append("(")
    expr.generateSourceCode(sb)
    sb.append(")")
}

fun org.azauner.minicpp.ast.node.NewArrayTypeFact.generateSourceCode(sb: StringBuilder) {
    sb.append("new ")
    type.generateSourceCode(sb)
    sb.append("[")
    expr.generateSourceCode(sb)
    sb.append("]")
}

fun org.azauner.minicpp.ast.node.ActionFact.generateSourceCode(sb: StringBuilder) {
    prefix?.let {
        sb.append(it.sourceCode)
    }
    sb.append(ident.name)
    actionOp?.generateSourceCode(sb)
    suffix?.let {
        sb.append(it.sourceCode)
    }
}

fun org.azauner.minicpp.ast.node.ActionOperation.generateSourceCode(sb: StringBuilder) {
    when (this) {
        is org.azauner.minicpp.ast.node.ArrayAccessOperation -> generateSourceCode(sb)
        is org.azauner.minicpp.ast.node.CallOperation -> generateSourceCode(sb)
    }
}

fun org.azauner.minicpp.ast.node.ArrayAccessOperation.generateSourceCode(sb: StringBuilder) {
    sb.append("[")
    expr.generateSourceCode(sb)
    sb.append("]")
}

fun org.azauner.minicpp.ast.node.CallOperation.generateSourceCode(sb: StringBuilder) {
    sb.append("(")
    actParList.forEachIndexed { index, expr ->
        expr.generateSourceCode(sb)
        if (index != actParList.lastIndex) {
            sb.append(", ")
        }
    }
    sb.append(")")
}
