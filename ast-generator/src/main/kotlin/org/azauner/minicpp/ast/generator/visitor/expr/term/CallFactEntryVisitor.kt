package org.azauner.minicpp.ast.generator.visitor.expr.term

import org.azauner.minicpp.ast.generator.visitor.IdentVisitor
import org.azauner.minicpp.ast.node.ARR_TYPES
import org.azauner.minicpp.ast.node.CallOperation
import org.azauner.minicpp.ast.util.getTerminalNodeFromTokenList
import org.azauner.minicpp.ast.util.getType
import org.azauner.minicpp.ast.util.requireSemantic
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class CallFactEntryVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.ActionFact>() {

    override fun visitCallFactEntry(ctx: minicppParser.CallFactEntryContext): org.azauner.minicpp.ast.node.ActionFact {
        val actionFact = org.azauner.minicpp.ast.node.ActionFact(
            prefix = ctx.preIncDec?.getTerminalNodeFromTokenList(ctx.INC_DEC())?.accept(IncDecVisitor()),
            ident = ctx.IDENT().accept(IdentVisitor()),
            suffix = ctx.postIncDec?.getTerminalNodeFromTokenList(ctx.INC_DEC())?.accept(IncDecVisitor()),
            actionOp = ctx.callFactEntryOperation()?.accept(CallFactEntryOperationVisitor(scope)),
            scope = scope
        )

        actionFact.run {
            when {
                actionOp != null && actionOp is org.azauner.minicpp.ast.node.CallOperation -> {
                    scope.getFunction(ident, (actionOp as CallOperation).actParList.map { it.getType() })
                }

                actionOp != null && actionOp is org.azauner.minicpp.ast.node.ArrayAccessOperation -> {
                    scope.checkVariableExists(actionFact.ident)
                    requireSemantic(scope.getVariable(actionFact.ident).type in ARR_TYPES) {
                        "Variable $ident is not an array"
                    }
                }
                else -> {
                    scope.checkVariableExists(actionFact.ident)

                }
            }

        }


        return actionFact
    }
}
