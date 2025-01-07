package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.util.ScopeHandler
import org.azauner.minicpp.ast.util.getTerminalNodeFromTokenList
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class CallFactEntryListener(
    private val callFactEntryOperationListener: CallFactEntryOperationListener,
    private val scopeHandler: ScopeHandler
) :
    minicppBaseListener() {

    private var callFactEntries = mutableListOf<org.azauner.minicpp.ast.node.ActionFact>()

    override fun exitCallFactEntry(ctx: minicppParser.CallFactEntryContext) {
        val prefix = ctx.preIncDec?.getTerminalNodeFromTokenList(ctx.INC_DEC())?.text?.getIncDec()
        val suffix = ctx.postIncDec?.getTerminalNodeFromTokenList(ctx.INC_DEC())?.text?.getIncDec()
        val scope = scopeHandler.getScope()
        callFactEntries.add(
            org.azauner.minicpp.ast.node.ActionFact(
                prefix = prefix,
                ident = org.azauner.minicpp.ast.node.Ident(ctx.IDENT().text),
                actionOp = ctx.callFactEntryOperation()
                    ?.let { callFactEntryOperationListener.getCallFactEntryOperation() },
                suffix = suffix,
                scope = scope
            )
        )
    }

    private fun String.getIncDec(): org.azauner.minicpp.ast.node.IncDec {
        return if (this == "++") {
            org.azauner.minicpp.ast.node.IncDec.INCREASE
        } else {
            org.azauner.minicpp.ast.node.IncDec.DECREASE
        }
    }

    fun getCallFactEntry(): org.azauner.minicpp.ast.node.ActionFact {
        return callFactEntries.removeLast()
    }
}
