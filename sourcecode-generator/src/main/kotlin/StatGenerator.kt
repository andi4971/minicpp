import org.azauner.ast.node.*
import java.awt.SystemColor.text

fun Stat.generateSourceCode(sb: StringBuilder) {
    when (this) {
        is BlockStat -> block.generateSourceCode(sb)
        BreakStat -> sb.appendLine("break;")
        is DeleteStat -> sb.appendLine("delete[] ${ident.name};")
        EmptyStat -> sb.appendLine(";")
        is ExprStat -> sb.appendLine("${expr.generateSourceCode(sb)};")
        is IfStat -> generateSourceCode(sb)
        is InputStat -> sb.appendLine("cin >> ${ident.name};")
        is OutputStat -> generateSourceCode(sb)
        is ReturnStat -> sb.appendLine("return ${expr?.generateSourceCode(sb)};")
        is WhileStat -> generateSourceCode(sb)
    }
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
        is Text -> sb.append(text)
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
