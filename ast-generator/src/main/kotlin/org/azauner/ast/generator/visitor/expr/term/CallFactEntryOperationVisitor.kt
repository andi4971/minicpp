package org.azauner.ast.generator.visitor.expr.term

import org.azauner.ast.generator.visitor.expr.ExprVisitor
import org.azauner.ast.node.ActionOperation
import org.azauner.ast.node.ArrayAccessOperation
import org.azauner.ast.node.CallOperation
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class CallFactEntryOperationVisitor(private val scope: Scope) : minicppBaseVisitor<ActionOperation>() {
    override fun visitExprFactOperation(ctx: minicppParser.ExprFactOperationContext): ActionOperation {
        return ArrayAccessOperation(ctx.expr().accept(ExprVisitor(scope)))
    }

    override fun visitActParListFactOperation(ctx: minicppParser.ActParListFactOperationContext): ActionOperation {

        return CallOperation(ctx.actParList()
            ?.expr()
            ?.map { it.accept(ExprVisitor(scope)) }
            .orEmpty())
    }
}
