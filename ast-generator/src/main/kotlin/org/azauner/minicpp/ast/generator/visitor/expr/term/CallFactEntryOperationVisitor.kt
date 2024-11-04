package org.azauner.minicpp.ast.generator.visitor.expr.term

import org.azauner.minicpp.ast.generator.visitor.expr.ExprVisitor
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class CallFactEntryOperationVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.ActionOperation>() {
    override fun visitExprFactOperation(ctx: minicppParser.ExprFactOperationContext): org.azauner.minicpp.ast.node.ActionOperation {
        return org.azauner.minicpp.ast.node.ArrayAccessOperation(ctx.expr().accept(ExprVisitor(scope)), scope)
    }

    override fun visitActParListFactOperation(ctx: minicppParser.ActParListFactOperationContext): org.azauner.minicpp.ast.node.ActionOperation {
        val actParList = ctx.actParList()
            ?.expr()
            ?.map { it.accept(ExprVisitor(scope)) }
            .orEmpty()
        return org.azauner.minicpp.ast.node.CallOperation(
            actParList,
            scope
        )
    }
}
