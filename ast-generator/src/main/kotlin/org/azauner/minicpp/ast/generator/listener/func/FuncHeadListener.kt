package org.azauner.minicpp.ast.generator.listener.func

import org.azauner.minicpp.ast.generator.listener.field.TypeListener
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class FuncHeadListener() :
    minicppBaseListener() {
    private lateinit var typeListener: TypeListener
    private lateinit var formParListListener: FormParListListener

    fun initListeners(typeListener: TypeListener, formParListListener: FormParListListener) {
        this.typeListener = typeListener
        this.formParListListener = formParListListener
    }

    private val funcHeads = mutableListOf<org.azauner.minicpp.ast.node.FuncHead>()

    override fun exitFuncHead(ctx: minicppParser.FuncHeadContext) {
        funcHeads.add(
            org.azauner.minicpp.ast.node.FuncHead(
                type = typeListener.getType(),
                ident = org.azauner.minicpp.ast.node.Ident(ctx.IDENT().text),
                formParList = ctx.formParList()?.let { formParListListener.getFormParList() }
            )
        )
    }

    fun getFuncHead(): org.azauner.minicpp.ast.node.FuncHead {
        return funcHeads.removeLast()
    }
}
