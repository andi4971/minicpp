package org.azauner.minicpp.ast.generator.listener

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class MiniCppListener(
    private val entryListener: MiniCppEntryListener,
    private val scopeHandler: ScopeHandler,
    private val className: String
) : minicppBaseListener() {

    lateinit var result: org.azauner.minicpp.ast.node.MiniCpp

    override fun exitMiniCpp(ctx: minicppParser.MiniCppContext) {
        result = org.azauner.minicpp.ast.node.MiniCpp(className, scopeHandler.getScope(), entryListener.getAllEntries())
    }

}
