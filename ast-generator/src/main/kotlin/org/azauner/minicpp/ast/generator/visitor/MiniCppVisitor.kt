package org.azauner.minicpp.ast.generator.visitor

import org.azauner.minicpp.ast.node.MiniCpp
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class MiniCppVisitor(private val className: String) : minicppBaseVisitor<MiniCpp>() {
    override fun visitMiniCpp(ctx: minicppParser.MiniCppContext): MiniCpp {
        val scope = Scope(null)
        val entries = ctx.miniCppEntry().map { it.accept(MiniCppEntryVisitor(scope)) }
        return MiniCpp(
            globalScope = scope,
            entries = entries,
            className = className
        )
    }
}
