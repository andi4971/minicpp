package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.minicpp.ast.util.ScopeHandler
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class CallFactEntryOperationListener(
    private val exprListener: ExprListener,
    private val actParListListener: ActParListListener,
    private val scopeHandler: ScopeHandler
) : minicppBaseListener() {

    private var actionOperations =
        Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.ActionOperation>())

    override fun exitActParListFactOperation(ctx: minicppParser.ActParListFactOperationContext) {
        val actParList = ctx.actParList()?.let { actParListListener.getActParList() }.orEmpty()
        val scope = scopeHandler.getScope()
        actionOperations.add(org.azauner.minicpp.ast.node.CallOperation(actParList, scope))
    }

    override fun exitExprFactOperation(ctx: minicppParser.ExprFactOperationContext) {
        val scope = scopeHandler.getScope()
        actionOperations.add(org.azauner.minicpp.ast.node.ArrayAccessOperation(exprListener.getExpr(), scope))
    }

    fun getCallFactEntryOperation(): org.azauner.minicpp.ast.node.ActionOperation {
        return actionOperations.removeLast()
    }
}
