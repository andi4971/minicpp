package org.azauner.ast.node

import aj.org.objectweb.asm.Opcodes.RETURN
import org.azauner.ast.SourceCodeGenerator
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.InsnNode

sealed interface Stat: BlockEntry

data object EmptyStat : Stat {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.appendLine(";")
    }

    override fun getInstructions(spix: HashMap<Ident, Int>): InsnList {
        return InsnList()
    }
}

data class BlockStat(val block: Block) : Stat {
    override fun generateSourceCode(sb: StringBuilder) {
        block.generateSourceCode(sb)
    }

    override fun getInstructions(spix: HashMap<Ident, Int>): InsnList {
        return block.getInstructions()
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

    override fun getInstructions(spix: HashMap<Ident, Int>): InsnList {

    }
}

data class InputStat(val ident: Ident): Stat {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("cin >> ")
        sb.append(ident.name)
        sb.appendLine(";")
    }

    override fun getInstructions(spix: HashMap<Ident, Int>): InsnList {

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

    override fun getInstructions(spix: HashMap<Ident, Int>): InsnList {
        return InsnList().apply {
            //TODO figure out how to load array

        }
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

    override fun getInstructions(spix: HashMap<Ident, Int>): InsnList {
        val instructions = InsnList()

        if(expr != null) {
            //instructions.add(expr.getInstructions())
        }

        instructions.add(InsnNode(RETURN))

        return instructions
    }
}
