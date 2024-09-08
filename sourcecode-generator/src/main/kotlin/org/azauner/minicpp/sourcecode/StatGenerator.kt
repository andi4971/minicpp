package org.azauner.minicpp.sourcecode

import org.azauner.minicpp.ast.node.*

fun Stat.generateSourceCode(sb: StringBuilder) {
    when (this) {
        is BlockStat -> block.generateSourceCode(sb)
        BreakStat -> sb.appendLine("break;")
        is DeleteStat -> sb.appendLine("delete[] ${ident.name};")
        EmptyStat -> sb.appendLine(";")
        is ExprStat -> generateSourceCode(sb)
        is IfStat -> generateSourceCode(sb)
        is InputStat -> sb.appendLine("cin >> ${ident.name};")
        is OutputStat -> generateSourceCode(sb)
        is ReturnStat -> generateSourceCode(sb)
        is WhileStat -> generateSourceCode(sb)
    }
}

fun ExprStat.generateSourceCode(sb: StringBuilder) {
    expr.generateSourceCode(sb)
    sb.appendLine(";")
}

fun ReturnStat.generateSourceCode(sb: StringBuilder) {
    sb.append("return ")
    expr?.generateSourceCode(sb)
    sb.appendLine(";")
}

fun OutputStat.generateSourceCode(sb: StringBuilder) {
    sb.append("cout")
    entries.forEach {
        sb.append(" << ")
        it.generateSourceCode(sb)
    }
    sb.appendLine(";")
}

fun OutputStatEntry.generateSourceCode(sb: StringBuilder) {
    when (this) {
        Endl -> sb.append("endl")
        is Expr -> generateSourceCode(sb)
        is Text -> sb.append(this.text)
    }
}

fun WhileStat.generateSourceCode(sb: StringBuilder) {
    sb.append("while (")
    condition.generateSourceCode(sb)
    sb.append(") ")
    whileStat.generateSourceCode(sb)
}

fun IfStat.generateSourceCode(sb: StringBuilder) {
    sb.append("if (")
    condition.generateSourceCode(sb)
    sb.append(") ")
    thenStat.generateSourceCode(sb)
    if (elseStat != null) {
        sb.append(" else ")
        elseStat!!.generateSourceCode(sb)
    }
}
