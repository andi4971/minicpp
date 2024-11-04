package org.azauner.minicpp.ast.node


sealed interface Stat : org.azauner.minicpp.ast.node.BlockEntry

data object EmptyStat : org.azauner.minicpp.ast.node.Stat

data class BlockStat(val block: org.azauner.minicpp.ast.node.Block) : org.azauner.minicpp.ast.node.Stat

data class ExprStat(val expr: org.azauner.minicpp.ast.node.Expr) : org.azauner.minicpp.ast.node.Stat
data class IfStat(
    val condition: org.azauner.minicpp.ast.node.Expr,
    val thenStat: org.azauner.minicpp.ast.node.Stat,
    val elseStat: org.azauner.minicpp.ast.node.Stat?
) :
    org.azauner.minicpp.ast.node.Stat

data class WhileStat(
    val condition: org.azauner.minicpp.ast.node.Expr,
    val whileStat: org.azauner.minicpp.ast.node.Stat
) :
    org.azauner.minicpp.ast.node.Stat

data object BreakStat : org.azauner.minicpp.ast.node.Stat

data class InputStat(
    val ident: org.azauner.minicpp.ast.node.Ident,
    val scope: org.azauner.minicpp.ast.node.scope.Scope
) :
    org.azauner.minicpp.ast.node.Stat

class OutputStat(val entries: List<org.azauner.minicpp.ast.node.OutputStatEntry>) : org.azauner.minicpp.ast.node.Stat


sealed interface OutputStatEntry

data class DeleteStat(
    val ident: org.azauner.minicpp.ast.node.Ident,
    val scope: org.azauner.minicpp.ast.node.scope.Scope
) :
    org.azauner.minicpp.ast.node.Stat

data class ReturnStat(val expr: org.azauner.minicpp.ast.node.Expr?) : org.azauner.minicpp.ast.node.Stat
