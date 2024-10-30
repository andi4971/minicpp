package org.azauner.minicpp.ast.generator.listener.stat

import org.azauner.minicpp.ast.node.Ident
import org.azauner.minicpp.ast.node.InputStat
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class InputStatListener: minicppBaseListener() {

    private var inputStats = Collections.synchronizedList(mutableListOf<InputStat>())

    override fun exitInputStat(ctx: minicppParser.InputStatContext) {
        val scope = Scope(null)
        //TODO
        inputStats.add(InputStat(ident = Ident(ctx.IDENT().text), scope))
    }

    fun getInputStat(): InputStat {
        return inputStats.removeLast()
    }
}
