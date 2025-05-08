package org.azauner.minicpp.ast.generator.listener

import org.azauner.minicpp.ast.node.MiniCpp
import org.azauner.minicpp.ast.util.ScopeHandler
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class MiniCppListener(
    private val entryListener: MiniCppEntryListener,
    private val scopeHandler: ScopeHandler,
    private val className: String
) : minicppBaseListener() {

    lateinit var result: MiniCpp

    override fun exitMiniCpp(ctx: minicppParser.MiniCppContext) {
        result = MiniCpp(className, scopeHandler.getScope(), entryListener.getAllEntries())
    }

}
