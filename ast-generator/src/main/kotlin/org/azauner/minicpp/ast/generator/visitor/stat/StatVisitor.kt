package org.azauner.minicpp.ast.generator.visitor.stat

import org.azauner.minicpp.ast.generator.visitor.IdentVisitor
import org.azauner.minicpp.ast.generator.visitor.block.BlockVisitor
import org.azauner.minicpp.ast.generator.visitor.expr.ExprVisitor
import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.minicpp.ast.util.getType
import org.azauner.minicpp.ast.util.requireSemantic
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class StatVisitor(private val scope: Scope) : minicppBaseVisitor<Stat>() {

    override fun visitEmptyStat(ctx: minicppParser.EmptyStatContext): Stat {
        return EmptyStat
    }

    override fun visitBlockStat(ctx: minicppParser.BlockStatContext): Stat {
        return BlockStat(ctx.block().accept(BlockVisitor(Scope(parent = scope))))
    }

    override fun visitExprStat(ctx: minicppParser.ExprStatContext): Stat {
        return ExprStat(ctx.expr().accept(ExprVisitor(scope)))
    }

    override fun visitIfStat(ctx: minicppParser.IfStatContext): Stat {
        return IfStat(
            condition = ctx.expr().accept(ExprVisitor(scope)),
            thenStat = ctx.stat().accept(StatVisitor(scope)),
            elseStat = ctx.elseStat()?.stat()?.accept(StatVisitor(scope))
        ).also {
            requireSemantic(it.condition.getType(scope) == ExprType.BOOL) { "If condition must be of type bool" }
        }
    }

    override fun visitWhileStat(ctx: minicppParser.WhileStatContext): Stat {
        return WhileStat(
            condition = ctx.expr().accept(ExprVisitor(scope)),
            whileStat = ctx.stat().accept(StatVisitor(scope))
        ).also {
            requireSemantic(it.condition.getType(scope) == ExprType.BOOL) { "While condition must be of type bool" }
        }
    }

    override fun visitBreakStat(ctx: minicppParser.BreakStatContext): Stat {
        return BreakStat
    }

    override fun visitInputStat(ctx: minicppParser.InputStatContext): Stat {
        val inputStat = InputStat(ctx.IDENT().accept(IdentVisitor()), scope)

        requireSemantic(scope.getVariable(inputStat.ident).type in INIT_TYPES_NOT_NULL) {
            "Input can only be used on non-pointer types"
        }

        return inputStat
    }

    override fun visitOutputStat(ctx: minicppParser.OutputStatContext): Stat {
        return OutputStat(ctx.outputStatEntry().map { it.accept(OutputStatEntryVisitor(scope)) })
    }


    override fun visitDeleteStat(ctx: minicppParser.DeleteStatContext): Stat {
        val deleteStat = DeleteStat(ctx.IDENT().accept(IdentVisitor()), scope)

        requireSemantic(scope.getVariable(deleteStat.ident).type in ARR_TYPES) {
            "Delete can only be used on pointers"
        }
        scope.checkVariableExists(deleteStat.ident)

        return deleteStat
    }

    override fun visitReturnStat(ctx: minicppParser.ReturnStatContext): Stat {
        return ReturnStat(ctx.expr()?.accept(ExprVisitor(scope)))
    }
}
