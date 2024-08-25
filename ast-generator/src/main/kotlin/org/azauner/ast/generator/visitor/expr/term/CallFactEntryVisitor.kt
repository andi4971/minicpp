package org.azauner.ast.generator.visitor.expr.term

import org.azauner.ast.generator.visitor.IdentVisitor
import org.azauner.ast.generator.visitor.util.getTerminalNodeFromTokenList
import org.azauner.ast.node.ActionFact
import org.azauner.ast.node.CallOperation
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class CallFactEntryVisitor(private val scope: Scope) : minicppBaseVisitor<ActionFact>() {

    override fun visitCallFactEntry(ctx: minicppParser.CallFactEntryContext): ActionFact {
        val actionFact =  ActionFact(
            prefix = ctx.preIncDec?.getTerminalNodeFromTokenList(ctx.INC_DEC())?.accept(IncDecVisitor()),
            ident = ctx.IDENT().accept(IdentVisitor()),
            suffix = ctx.postIncDec?.getTerminalNodeFromTokenList(ctx.INC_DEC())?.accept(IncDecVisitor()),
            actionOp = ctx.callFactEntryOperation()?.accept(CallFactEntryOperationVisitor(scope))
        )
        actionFact.run {
            if(actionOp != null && actionOp is CallOperation) {
                scope.checkFunctionExists(ident, actionOp.actParList)
            }
        }

        scope.checkVariableExists(actionFact.ident)

        return actionFact
    }
}
