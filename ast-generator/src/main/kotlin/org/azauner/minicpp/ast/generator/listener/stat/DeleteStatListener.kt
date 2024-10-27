package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.node.DeleteStat
import org.azauner.minicpp.ast.node.Ident
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class DeleteStatListener: minicppBaseListener() {

    private var deleteStats = mutableListOf<DeleteStat>()

    override fun exitDeleteStat(ctx: minicppParser.DeleteStatContext) {
        val scope = Scope(null)
        //TODO
        deleteStats.add(DeleteStat(Ident(ctx.IDENT().text), scope))
    }

    fun getDeleteStat(): DeleteStat {
        return deleteStats.removeLast()
    }
}
