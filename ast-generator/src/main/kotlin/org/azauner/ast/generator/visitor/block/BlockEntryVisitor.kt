package org.azauner.ast.generator.visitor.block

import org.azauner.ast.generator.visitor.field.ConstDefVisitor
import org.azauner.ast.generator.visitor.field.VarDefVisitor
import org.azauner.ast.generator.visitor.stat.StatVisitor
import org.azauner.ast.node.BlockEntry
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class BlockEntryVisitor(private val scope: Scope) : minicppBaseVisitor<BlockEntry>() {

    override fun visitConstDef(ctx: minicppParser.ConstDefContext): BlockEntry {
        return ctx.accept(ConstDefVisitor(scope))
    }

    override fun visitVarDef(ctx: minicppParser.VarDefContext): BlockEntry {
        return ctx.accept(VarDefVisitor(scope))
    }

    override fun visitStat(ctx: minicppParser.StatContext): BlockEntry {
        return ctx.accept(StatVisitor(scope))
    }
}
