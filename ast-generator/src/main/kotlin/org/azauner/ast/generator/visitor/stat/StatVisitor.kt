package org.azauner.ast.generator.visitor.stat

import org.azauner.ast.generator.visitor.IdentVisitor
import org.azauner.ast.generator.visitor.block.BlockVisitor
import org.azauner.ast.node.*
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class StatVisitor: minicppBaseVisitor<Stat>() {

    override fun visitEmptyStat(ctx: minicppParser.EmptyStatContext): Stat {
        return EmptyStat
    }

    override fun visitBlockStat(ctx: minicppParser.BlockStatContext): Stat {
        return BlockStat(ctx.block().accept(BlockVisitor()))
    }

    override fun visitExprStat(ctx: minicppParser.ExprStatContext): Stat {
        return ExprStat(ctx.expr().accept(ExprVisitor()))
    }

    override fun visitIfStat(ctx: minicppParser.IfStatContext): Stat {
    }

    override fun visitWhileStat(ctx: minicppParser.WhileStatContext): Stat {
    }

    override fun visitBreakStat(ctx: minicppParser.BreakStatContext): Stat {
        return BreakStat
    }

    override fun visitInputStat(ctx: minicppParser.InputStatContext): Stat {
        return InputStat(ctx.IDENT().accept(IdentVisitor()))
    }

    override fun visitOutputStat(ctx: minicppParser.OutputStatContext): Stat {

    }


    override fun visitDeleteStat(ctx: minicppParser.DeleteStatContext): Stat {
        return DeleteStat(ctx.IDENT().accept(IdentVisitor()))
    }

    override fun visitReturnStat(ctx: minicppParser.ReturnStatContext): Stat {
    }
}
