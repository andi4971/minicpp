package org.azauner.minicpp.ast.generator.listener.func

import org.azauner.minicpp.ast.generator.listener.block.BlockListener
import org.azauner.minicpp.ast.util.ScopeHandler
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class FuncDefListener(
    private val funcHeadListener: FuncHeadListener, private val blockListener: BlockListener,
    private val scopeHandler: ScopeHandler
) : minicppBaseListener() {

    private val funcDefs = mutableListOf<org.azauner.minicpp.ast.node.FuncDef>()

    var addNextFormParListToVariables = false

    override fun enterFuncDef(ctx: minicppParser.FuncDefContext?) {
        scopeHandler.pushChildScope()
        addNextFormParListToVariables = true
    }


    override fun exitFuncDef(ctx: minicppParser.FuncDefContext) {
        addNextFormParListToVariables = false
        val funcDef = org.azauner.minicpp.ast.node.FuncDef(funcHeadListener.getFuncHead(), blockListener.getBlock())
        funcDefs.add(funcDef)

        scopeHandler.popScope()

        funcDef.funHead.run {
            scopeHandler.getScope().addFunction(ident, type, formParList, true)
        }
    }

    fun getFuncDef(): org.azauner.minicpp.ast.node.FuncDef {
        return funcDefs.removeLast()
    }
}
