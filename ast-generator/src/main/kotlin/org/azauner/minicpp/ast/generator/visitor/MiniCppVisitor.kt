package org.azauner.minicpp.ast.generator.visitor

import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class MiniCppVisitor(private val className: String) : minicppBaseVisitor<org.azauner.minicpp.ast.node.MiniCpp>() {
    override fun visitMiniCpp(ctx: minicppParser.MiniCppContext): org.azauner.minicpp.ast.node.MiniCpp {
        val scope = org.azauner.minicpp.ast.node.scope.Scope(null)
        val entries = ctx.miniCppEntry().map { it.accept(MiniCppEntryVisitor(scope)) }
        return org.azauner.minicpp.ast.node.MiniCpp(
            globalScope = scope,
            entries = entries,
            className = className
        )
    }
}
