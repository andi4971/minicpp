package org.azauner.minicpp.ast.node

sealed interface Stat: BlockEntry

data object EmptyStat : Stat

data class BlockStat(val block: Block) : Stat

data class ExprStat(val expr: Expr): Stat
data class IfStat(val condition: Expr, val thenStat: Stat, val elseStat: Stat?): Stat

data class WhileStat(val condition: Expr, val whileStat: Stat): Stat

data object BreakStat : Stat

data class InputStat(val ident: Ident): Stat

class OutputStat(val entries: List<OutputStatEntry>) : Stat


sealed interface OutputStatEntry

data class DeleteStat(val ident: Ident): Stat

data class ReturnStat(val expr: Expr?): Stat
