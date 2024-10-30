package org.azauner.minicpp.ast.generator.listener

import org.azauner.minicpp.ast.node.MiniCpp
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class MiniCppListener(private val entryListener: MiniCppEntryListener): minicppBaseListener() {

    lateinit var result: MiniCpp


    override fun exitMiniCpp(ctx: minicppParser.MiniCppContext) {
        result = MiniCpp("test", Scope(null), entryListener.getAllEntries())
    }

}
