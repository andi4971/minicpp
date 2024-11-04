package org.azauner.minicpp.ast.generator.visitor.block

import org.azauner.minicpp.ast.generator.visitor.field.ConstDefVisitor
import org.azauner.minicpp.ast.generator.visitor.field.VarDefVisitor
import org.azauner.minicpp.ast.generator.visitor.stat.StatVisitor
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class BlockEntryVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.BlockEntry>() {

    override fun visitConstDef(ctx: minicppParser.ConstDefContext): org.azauner.minicpp.ast.node.BlockEntry {
        return ctx.accept(ConstDefVisitor(scope))
    }

    override fun visitVarDef(ctx: minicppParser.VarDefContext): org.azauner.minicpp.ast.node.BlockEntry {
        return ctx.accept(VarDefVisitor(scope))
    }

    override fun visitStat(ctx: minicppParser.StatContext): org.azauner.minicpp.ast.node.BlockEntry {
        return ctx.accept(StatVisitor(scope))
    }
}
