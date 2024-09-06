package org.azauner.ast.generator.visitor

import org.azauner.ast.node.MiniCpp
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class MiniCppVisitor(private val className: String) : minicppBaseVisitor<MiniCpp>() {
    override fun visitMiniCpp(ctx: minicppParser.MiniCppContext): MiniCpp {
        val scope = Scope(null)
        val entries = ctx.miniCppEntry().map { it.accept(MiniCppEntryVisitor(scope)) }
        return MiniCpp(
            globalScope = scope,
            entries = entries,
            fileName = className
        )
    }
}
