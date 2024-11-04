package org.azauner.minicpp.sourcecode

fun org.azauner.minicpp.ast.node.Stat.generateSourceCode(sb: StringBuilder) {
    when (this) {
        is org.azauner.minicpp.ast.node.BlockStat -> block.generateSourceCode(sb)
        org.azauner.minicpp.ast.node.BreakStat -> sb.appendLine("break;")
        is org.azauner.minicpp.ast.node.DeleteStat -> sb.appendLine("delete[] ${ident.name};")
        org.azauner.minicpp.ast.node.EmptyStat -> sb.appendLine(";")
        is org.azauner.minicpp.ast.node.ExprStat -> generateSourceCode(sb)
        is org.azauner.minicpp.ast.node.IfStat -> generateSourceCode(sb)
        is org.azauner.minicpp.ast.node.InputStat -> sb.appendLine("cin >> ${ident.name};")
        is org.azauner.minicpp.ast.node.OutputStat -> generateSourceCode(sb)
        is org.azauner.minicpp.ast.node.ReturnStat -> generateSourceCode(sb)
        is org.azauner.minicpp.ast.node.WhileStat -> generateSourceCode(sb)
    }
}

fun org.azauner.minicpp.ast.node.ExprStat.generateSourceCode(sb: StringBuilder) {
    expr.generateSourceCode(sb)
    sb.appendLine(";")
}

fun org.azauner.minicpp.ast.node.ReturnStat.generateSourceCode(sb: StringBuilder) {
    sb.append("return ")
    expr?.generateSourceCode(sb)
    sb.appendLine(";")
}

fun org.azauner.minicpp.ast.node.OutputStat.generateSourceCode(sb: StringBuilder) {
    sb.append("cout")
    entries.forEach {
        sb.append(" << ")
        it.generateSourceCode(sb)
    }
    sb.appendLine(";")
}

fun org.azauner.minicpp.ast.node.OutputStatEntry.generateSourceCode(sb: StringBuilder) {
    when (this) {
        org.azauner.minicpp.ast.node.Endl -> sb.append("endl")
        is org.azauner.minicpp.ast.node.Expr -> generateSourceCode(sb)
        is org.azauner.minicpp.ast.node.Text -> sb.append(this.text)
    }
}

fun org.azauner.minicpp.ast.node.WhileStat.generateSourceCode(sb: StringBuilder) {
    sb.append("while (")
    condition.generateSourceCode(sb)
    sb.append(") ")
    whileStat.generateSourceCode(sb)
}

fun org.azauner.minicpp.ast.node.IfStat.generateSourceCode(sb: StringBuilder) {
    sb.append("if (")
    condition.generateSourceCode(sb)
    sb.append(") ")
    thenStat.generateSourceCode(sb)
    if (elseStat != null) {
        sb.append(" else ")
        elseStat!!.generateSourceCode(sb)
    }
}
