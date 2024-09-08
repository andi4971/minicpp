package org.azauner.ast.node

import aj.org.objectweb.asm.Opcodes.RETURN
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.InsnNode

sealed interface Stat: BlockEntry

data object EmptyStat : Stat {

    override fun getInstructions(spix: HashMap<Ident, Int>): InsnList {
        return InsnList()
    }
}

data class BlockStat(val block: Block) : Stat {

    override fun getInstructions(spix: HashMap<Ident, Int>): InsnList {
        return block.getInstructions()
    }
}

data class ExprStat(val expr: Expr): Stat
data class IfStat(val condition: Expr, val thenStat: Stat, val elseStat: Stat?): Stat

data class WhileStat(val condition: Expr, val whileStat: Stat): Stat

data object BreakStat : Stat {

    override fun getInstructions(spix: HashMap<Ident, Int>): InsnList {

    }
}

data class InputStat(val ident: Ident): Stat {

    override fun getInstructions(spix: HashMap<Ident, Int>): InsnList {

    }
}

class OutputStat(val entries: List<OutputStatEntry>) : Stat


sealed interface OutputStatEntry

data class DeleteStat(val ident: Ident): Stat {

    override fun getInstructions(spix: HashMap<Ident, Int>): InsnList {
        return InsnList().apply {
            //TODO figure out how to load array

        }
    }
}

data class ReturnStat(val expr: Expr?): Stat {

    override fun getInstructions(spix: HashMap<Ident, Int>): InsnList {
        val instructions = InsnList()

        if(expr != null) {
            //instructions.add(expr.getInstructions())
        }

        instructions.add(InsnNode(RETURN))

        return instructions
    }
}
