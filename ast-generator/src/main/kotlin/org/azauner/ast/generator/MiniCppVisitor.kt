package org.azauner.ast.generator

import org.azauner.ast.node.MiniCpp
import org.azauner.ast.node.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class MiniCppVisitor: minicppBaseVisitor<MiniCpp>() {
    override fun visitMiniCpp(ctx: minicppParser.MiniCppContext): MiniCpp {
        val entries = ctx.miniCppEntry().map { it.accept(MiniCppEntryVisitor()) }
        return MiniCpp(
            globalScope = Scope(null),
            entries = entries
        )
    }
}
