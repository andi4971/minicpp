package org.azauner.minicpp.ast.generator.listener.func

import org.azauner.minicpp.ast.generator.listener.field.TypeListener
import org.azauner.minicpp.ast.node.FuncHead
import org.azauner.minicpp.ast.node.Ident
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class FuncHeadListener(private val typeListener: TypeListener, private val formParListListener: FormParListListener) :
    minicppBaseListener() {

    private val funcHeads = mutableListOf<FuncHead>()

    override fun exitFuncHead(ctx: minicppParser.FuncHeadContext) {
        funcHeads.add(
            FuncHead(
                type = typeListener.getType(),
                ident = Ident(ctx.IDENT().text),
                formParList = ctx.formParList()?.let { formParListListener.getFormParList() }
            )
        )
    }

    fun getFuncHead(): FuncHead {
        return funcHeads.removeLast()
    }
}
