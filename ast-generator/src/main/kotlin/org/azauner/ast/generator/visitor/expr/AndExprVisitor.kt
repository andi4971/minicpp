package org.azauner.ast.generator.visitor.expr

import org.azauner.ast.node.AndExpr
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class AndExprVisitor: minicppBaseVisitor<AndExpr>() {

    override fun visitAndExpr(ctx: minicppParser.AndExprContext): AndExpr {
        return AndExpr(ctx.relExpr().map { it.accept(RelExprVisitor()) })
    }
}