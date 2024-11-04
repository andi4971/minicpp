package org.azauner.minicpp.ast.generator.listener.func

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class FuncDeclListener(
    private val funcHeadListener: FuncHeadListener,
) : minicppBaseListener() {

    private val funcDecls = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.FuncDecl>())

    override fun exitFuncDecl(ctx: minicppParser.FuncDeclContext) {
        funcDecls.add(org.azauner.minicpp.ast.node.FuncDecl(funcHeadListener.getFuncHead()))

    }

    fun getFuncDecl(): org.azauner.minicpp.ast.node.FuncDecl {
        return funcDecls.removeLast()
    }
}
