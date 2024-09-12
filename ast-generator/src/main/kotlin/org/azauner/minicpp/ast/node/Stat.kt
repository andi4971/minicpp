package org.azauner.minicpp.ast.node

import org.azauner.minicpp.ast.node.scope.Scope


sealed interface Stat: BlockEntry

data object EmptyStat : Stat

data class BlockStat(val block: Block) : Stat

data class ExprStat(val expr: Expr): Stat
data class IfStat(val condition: Expr, val thenStat: Stat, val elseStat: Stat?): Stat

data class WhileStat(val condition: Expr, val whileStat: Stat): Stat

data object BreakStat : Stat

data class InputStat(val ident: Ident,val  scope: Scope): Stat

class OutputStat(val entries: List<OutputStatEntry>) : Stat


sealed interface OutputStatEntry

data class DeleteStat(val ident: Ident,val  scope: Scope): Stat

data class ReturnStat(val expr: Expr?): Stat
