package org.azauner.minicpp.ast.generator.listener.func

import org.azauner.minicpp.ast.util.ScopeHandler
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class FuncDeclListener(
    private val funcHeadListener: FuncHeadListener,
    private val scopeHandler: ScopeHandler
) : minicppBaseListener() {

    private val funcDecls = mutableListOf<org.azauner.minicpp.ast.node.FuncDecl>()

    override fun exitFuncDecl(ctx: minicppParser.FuncDeclContext) {
        val funcDecl = org.azauner.minicpp.ast.node.FuncDecl(funcHeadListener.getFuncHead())
        funcDecls.add(funcDecl)
        funcDecl.funcHead.run {
            scopeHandler.getScope().addFunction(ident, type, formParList, false)
        }

    }

    fun getFuncDecl(): org.azauner.minicpp.ast.node.FuncDecl {
        return funcDecls.removeLast()
    }
}
