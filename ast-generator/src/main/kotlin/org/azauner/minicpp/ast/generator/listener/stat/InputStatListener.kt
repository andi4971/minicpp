package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.util.ScopeHandler
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class InputStatListener(private val scopeHandler: ScopeHandler): minicppBaseListener() {

    private var inputStats = mutableListOf<org.azauner.minicpp.ast.node.InputStat>()

    override fun exitInputStat(ctx: minicppParser.InputStatContext) {
        val scope = scopeHandler.getScope()
        inputStats.add(
            org.azauner.minicpp.ast.node.InputStat(
                ident = org.azauner.minicpp.ast.node.Ident(ctx.IDENT().text),
                scope
            )
        )
    }

    fun getInputStat(): org.azauner.minicpp.ast.node.InputStat {
        return inputStats.removeLast()
    }
}
