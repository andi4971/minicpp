package org.azauner.ast.generator.visitor.expr

import org.azauner.ast.node.RelExpr
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class RelExprVisitor: minicppBaseVisitor<RelExpr>() {

    override fun visitRelExpr(ctx: minicppParser.RelExprContext): RelExpr {
        return RelExpr(addExpressions = ctx.addExpr().map { it.accept(AddExprVisitor()) })
    }
}
