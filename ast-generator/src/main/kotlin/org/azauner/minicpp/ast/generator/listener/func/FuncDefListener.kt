package org.azauner.minicpp.ast.generator.listener.func

import org.azauner.minicpp.ast.generator.listener.block.BlockListener
import org.azauner.minicpp.ast.node.FuncDef
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class FuncDefListener(private val funcHeadListener: FuncHeadListener, private val blockListener: BlockListener): minicppBaseListener() {

    private val funcDefs = Collections.synchronizedList(mutableListOf<FuncDef>())

    override fun exitFuncDef(ctx: minicppParser.FuncDefContext) {
        funcDefs.add(FuncDef(funcHeadListener.getFuncHead(), blockListener.getBlock()))
    }

    fun getFuncDef(): FuncDef {
        return funcDefs.removeLast()
    }
}
