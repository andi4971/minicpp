package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

sealed interface Stat: BlockEntry

data object EmptyStat : Stat {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.appendLine(";")
    }
}

data class BlockStat(val block: Block) : Stat {
    override fun generateSourceCode(sb: StringBuilder) {
        block.generateSourceCode(sb)
    }
}

data class ExprStat(val expr: Expr): Stat {
    override fun generateSourceCode(sb: StringBuilder) {
        expr.generateSourceCode(sb)
        sb.appendLine(";")
    }
}

data class IfStat(val condition: Expr, val thenStat: Stat, val elseStat: Stat?): Stat {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("if (")
        condition.generateSourceCode(sb)
        sb.append(") ")
        thenStat.generateSourceCode(sb)
        if (elseStat != null) {
            sb.append(" else ")
            elseStat.generateSourceCode(sb)
        }
    }
}

data class WhileStat(val condition: Expr, val whileStat: Stat): Stat {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("while (")
        condition.generateSourceCode(sb)
        sb.append(") ")
        whileStat.generateSourceCode(sb)
    }
}

data object BreakStat : Stat {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.appendLine("break;")
    }
}

data class InputStat(val ident: Ident): Stat {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("cin >> ")
        sb.append(ident.name)
        sb.appendLine(";")
    }
}

class OutputStat(val entries: List<OutputStatEntry>) : Stat {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("cout")
        entries.forEach {
            sb.append(" << ")
            it.generateSourceCode(sb)
        }
        sb.appendLine(";")
    }
}

sealed interface OutputStatEntry: SourceCodeGenerator

data class DeleteStat(val ident: Ident): Stat {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.appendLine("delete[] ${ident.name};")
    }
}

data class ReturnStat(val expr: Expr?): Stat {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("return")
        if (expr != null) {
            sb.append(" ")
            expr.generateSourceCode(sb)
        }
        sb.appendLine(";")
    }
}
