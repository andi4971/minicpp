package org.azauner.minicpp.ast.generator.visitor.expr.term

import org.azauner.minicpp.ast.generator.visitor.expr.ExprVisitor
import org.azauner.minicpp.ast.node.ActionOperation
import org.azauner.minicpp.ast.node.ArrayAccessOperation
import org.azauner.minicpp.ast.node.CallOperation
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class CallFactEntryOperationVisitor(private val scope: Scope) : minicppBaseVisitor<ActionOperation>() {
    override fun visitExprFactOperation(ctx: minicppParser.ExprFactOperationContext): ActionOperation {
        return ArrayAccessOperation(ctx.expr().accept(ExprVisitor(scope)), scope)
    }

    override fun visitActParListFactOperation(ctx: minicppParser.ActParListFactOperationContext): ActionOperation {
        val actParList = ctx.actParList()
            ?.expr()
            ?.map { it.accept(ExprVisitor(scope)) }
            .orEmpty()
        return CallOperation(
            actParList,
            scope
        )
    }
}
