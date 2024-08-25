package org.azauner.ast.generator.visitor.stat

import org.azauner.ast.generator.visitor.expr.ExprVisitor
import org.azauner.ast.node.Endl
import org.azauner.ast.node.OutputStatEntry
import org.azauner.ast.node.Text
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class OutputStatEntryVisitor(private val scope: Scope) : minicppBaseVisitor<OutputStatEntry>() {
    override fun visitExprOutputStatEntry(ctx: minicppParser.ExprOutputStatEntryContext): OutputStatEntry {
        return ctx.expr().accept(ExprVisitor(scope))
    }

    override fun visitStringOutputStatEntry(ctx: minicppParser.StringOutputStatEntryContext): OutputStatEntry {
        return Text(ctx.STRING().text)
    }

    override fun visitEndlOutputStatEntry(ctx: minicppParser.EndlOutputStatEntryContext): OutputStatEntry {
        return Endl
    }
}
