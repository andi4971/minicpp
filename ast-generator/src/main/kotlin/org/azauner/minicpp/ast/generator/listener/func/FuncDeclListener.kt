package org.azauner.minicpp.ast.generator.listener.func

import org.azauner.minicpp.ast.node.FuncDecl
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class FuncDeclListener(private val funcHeadListener: FuncHeadListener): minicppBaseListener() {

    private val funcDecls = mutableListOf<FuncDecl>()

    override fun exitFuncDecl(ctx: minicppParser.FuncDeclContext) {
        funcDecls.add(FuncDecl(funcHeadListener.getFuncHead()))
    }

    fun getFuncDecl(): FuncDecl {
        return funcDecls.removeLast()
    }
}
