package org.azauner.minicpp.ast.generator.visitor.stat

import org.azauner.minicpp.ast.generator.visitor.IdentVisitor
import org.azauner.minicpp.ast.generator.visitor.block.BlockVisitor
import org.azauner.minicpp.ast.generator.visitor.expr.ExprVisitor
import org.azauner.minicpp.ast.node.ARR_TYPES
import org.azauner.minicpp.ast.node.ExprType
import org.azauner.minicpp.ast.node.INIT_TYPES_NOT_NULL
import org.azauner.minicpp.ast.util.getType
import org.azauner.minicpp.ast.util.requireSemantic
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class StatVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.Stat>() {

    override fun visitEmptyStat(ctx: minicppParser.EmptyStatContext): org.azauner.minicpp.ast.node.Stat {
        return org.azauner.minicpp.ast.node.EmptyStat
    }

    override fun visitBlockStat(ctx: minicppParser.BlockStatContext): org.azauner.minicpp.ast.node.Stat {
        return org.azauner.minicpp.ast.node.BlockStat(
            ctx.block().accept(BlockVisitor(org.azauner.minicpp.ast.node.scope.Scope(parent = scope)))
        )
    }

    override fun visitExprStat(ctx: minicppParser.ExprStatContext): org.azauner.minicpp.ast.node.Stat {
        return org.azauner.minicpp.ast.node.ExprStat(ctx.expr().accept(ExprVisitor(scope)))
    }

    override fun visitIfStat(ctx: minicppParser.IfStatContext): org.azauner.minicpp.ast.node.Stat {
        return org.azauner.minicpp.ast.node.IfStat(
            condition = ctx.expr().accept(ExprVisitor(scope)),
            thenStat = ctx.stat().accept(StatVisitor(scope)),
            elseStat = ctx.elseStat()?.stat()?.accept(StatVisitor(scope))
        ).also {
            requireSemantic(it.condition.getType() == ExprType.BOOL) { "If condition must be of type bool" }
        }
    }

    override fun visitWhileStat(ctx: minicppParser.WhileStatContext): org.azauner.minicpp.ast.node.Stat {
        return org.azauner.minicpp.ast.node.WhileStat(
            condition = ctx.expr().accept(ExprVisitor(scope)),
            whileStat = ctx.stat().accept(StatVisitor(scope))
        ).also {
            requireSemantic(it.condition.getType() == ExprType.BOOL) { "While condition must be of type bool" }
        }
    }

    override fun visitBreakStat(ctx: minicppParser.BreakStatContext): org.azauner.minicpp.ast.node.Stat {
        return org.azauner.minicpp.ast.node.BreakStat
    }

    override fun visitInputStat(ctx: minicppParser.InputStatContext): org.azauner.minicpp.ast.node.Stat {
        val inputStat = org.azauner.minicpp.ast.node.InputStat(ctx.IDENT().accept(IdentVisitor()), scope)

        requireSemantic(scope.getVariable(inputStat.ident).type in INIT_TYPES_NOT_NULL) {
            "Input can only be used on non-pointer types"
        }

        return inputStat
    }

    override fun visitOutputStat(ctx: minicppParser.OutputStatContext): org.azauner.minicpp.ast.node.Stat {
        return org.azauner.minicpp.ast.node.OutputStat(
            ctx.outputStatEntry().map { it.accept(OutputStatEntryVisitor(scope)) })
    }


    override fun visitDeleteStat(ctx: minicppParser.DeleteStatContext): org.azauner.minicpp.ast.node.Stat {
        val deleteStat = org.azauner.minicpp.ast.node.DeleteStat(ctx.IDENT().accept(IdentVisitor()), scope)

        requireSemantic(scope.getVariable(deleteStat.ident).type in ARR_TYPES) {
            "Delete can only be used on pointers"
        }
        scope.checkVariableExists(deleteStat.ident)

        return deleteStat
    }

    override fun visitReturnStat(ctx: minicppParser.ReturnStatContext): org.azauner.minicpp.ast.node.Stat {
        return org.azauner.minicpp.ast.node.ReturnStat(ctx.expr()?.accept(ExprVisitor(scope)))
    }
}
