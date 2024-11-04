package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.generator.listener.ScopeHandler
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class DeleteStatListener(private val scopeHandler: ScopeHandler): minicppBaseListener() {

    private var deleteStats = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.DeleteStat>())

    override fun exitDeleteStat(ctx: minicppParser.DeleteStatContext) {
        val scope =scopeHandler.getScope()
        deleteStats.add(
            org.azauner.minicpp.ast.node.DeleteStat(
                org.azauner.minicpp.ast.node.Ident(ctx.IDENT().text),
                scope
            )
        )
    }

    fun getDeleteStat(): org.azauner.minicpp.ast.node.DeleteStat {
        return deleteStats.removeLast()
    }
}
