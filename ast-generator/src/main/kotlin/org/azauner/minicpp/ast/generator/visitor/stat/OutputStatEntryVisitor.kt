package org.azauner.minicpp.ast.generator.visitor.stat

import org.azauner.minicpp.ast.generator.visitor.expr.ExprVisitor
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class OutputStatEntryVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.OutputStatEntry>() {
    override fun visitExprOutputStatEntry(ctx: minicppParser.ExprOutputStatEntryContext): org.azauner.minicpp.ast.node.OutputStatEntry {
        return ctx.expr().accept(ExprVisitor(scope))
    }

    override fun visitStringOutputStatEntry(ctx: minicppParser.StringOutputStatEntryContext): org.azauner.minicpp.ast.node.OutputStatEntry {
        return org.azauner.minicpp.ast.node.Text(
            ctx.STRING().text.trim('"')
                .replace("\\\\", "\\")
                .replace("\\t", "\t")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\\"", "\"")
        )
    }

    override fun visitEndlOutputStatEntry(ctx: minicppParser.EndlOutputStatEntryContext): org.azauner.minicpp.ast.node.OutputStatEntry {
        return org.azauner.minicpp.ast.node.Endl
    }
}
