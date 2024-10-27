package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.node.ActionFact
import org.azauner.minicpp.ast.node.Ident
import org.azauner.minicpp.ast.node.IncDec
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.minicpp.ast.util.getTerminalNodeFromTokenList
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class CallFactEntryListener(private val callFactEntryOperationListener: CallFactEntryOperationListener) :
    minicppBaseListener() {

    private var callFactEntries = mutableListOf<ActionFact>()

    override fun exitCallFactEntry(ctx: minicppParser.CallFactEntryContext) {
        val prefix = ctx.preIncDec?.getTerminalNodeFromTokenList(ctx.INC_DEC())?.text?.getIncDec()
        val suffix = ctx.postIncDec?.getTerminalNodeFromTokenList(ctx.INC_DEC())?.text?.getIncDec()
        //todo
        val scope = Scope(null)
        callFactEntries.add(
            ActionFact(
                prefix = prefix,
                ident = Ident(ctx.IDENT().text),
                actionOp = ctx.callFactEntryOperation()
                    ?.let { callFactEntryOperationListener.getCallFactEntryOperation() },
                suffix = suffix,
                scope = scope
            )
        )
    }

    private fun String.getIncDec(): IncDec {
        return if (this == "++") {
            IncDec.INCREASE
        } else {
            IncDec.DECREASE
        }
    }

    fun getCallFactEntry(): ActionFact {
        return callFactEntries.removeLast()
    }
}
