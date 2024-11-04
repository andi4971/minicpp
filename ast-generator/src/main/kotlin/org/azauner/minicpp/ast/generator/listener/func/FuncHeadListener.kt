package org.azauner.minicpp.ast.generator.listener.func

import org.azauner.minicpp.ast.generator.listener.field.TypeListener
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class FuncHeadListener(private val typeListener: TypeListener, private val formParListListener: FormParListListener) :
    minicppBaseListener() {

    private val funcHeads = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.FuncHead>())

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
