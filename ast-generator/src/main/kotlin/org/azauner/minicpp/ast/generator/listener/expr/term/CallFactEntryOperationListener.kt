package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.minicpp.ast.node.ActionOperation
import org.azauner.minicpp.ast.node.ArrayAccessOperation
import org.azauner.minicpp.ast.node.CallOperation
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class CallFactEntryOperationListener(
    private val exprListener: ExprListener,
    private val actParListListener: ActParListListener
) : minicppBaseListener() {

    private var actionOperations = mutableListOf<ActionOperation>()

    override fun exitActParListFactOperation(ctx: minicppParser.ActParListFactOperationContext) {
        val actParList = ctx.actParList()?.let { actParListListener.getActParList() }.orEmpty()
        //TODO
        val scope = Scope(null)
        actionOperations.add(CallOperation(actParList, scope))
    }

    override fun exitExprFactOperation(ctx: minicppParser.ExprFactOperationContext) {
        val scope = Scope(null)
        //Todo
        actionOperations.add(ArrayAccessOperation(exprListener.getExpr(), scope))
    }

    fun getCallFactEntryOperation(): ActionOperation {
        return actionOperations.removeLast()
    }
}
