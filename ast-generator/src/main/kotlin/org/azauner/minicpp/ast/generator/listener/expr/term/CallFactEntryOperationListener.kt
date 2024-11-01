package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.generator.listener.ScopeHandler
import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.minicpp.ast.node.ActionOperation
import org.azauner.minicpp.ast.node.ArrayAccessOperation
import org.azauner.minicpp.ast.node.CallOperation
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class CallFactEntryOperationListener(
    private val exprListener: ExprListener,
    private val actParListListener: ActParListListener,
    private val scopeHandler: ScopeHandler
) : minicppBaseListener() {

    private var actionOperations = Collections.synchronizedList(mutableListOf<ActionOperation>())

    override fun exitActParListFactOperation(ctx: minicppParser.ActParListFactOperationContext) {
        val actParList = ctx.actParList()?.let { actParListListener.getActParList() }.orEmpty()
        val scope = scopeHandler.getScope()
        actionOperations.add(CallOperation(actParList, scope))
    }

    override fun exitExprFactOperation(ctx: minicppParser.ExprFactOperationContext) {
        val scope = scopeHandler.getScope()
        actionOperations.add(ArrayAccessOperation(exprListener.getExpr(), scope))
    }

    fun getCallFactEntryOperation(): ActionOperation {
        return actionOperations.removeLast()
    }
}
