package org.azauner.ast.generator.visitor.expr

import org.azauner.ast.node.SimpleExpr
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class SimpleExprVisitor: minicppBaseVisitor<SimpleExpr>() {

    override fun visitSimpleExpr(ctx: minicppParser.SimpleExprContext): SimpleExpr {
    
    }
}
