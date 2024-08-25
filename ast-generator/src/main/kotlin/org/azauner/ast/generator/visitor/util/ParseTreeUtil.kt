package org.azauner.ast.generator.visitor.util

import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.ast.node.Expr
import org.azauner.ast.node.OrExpr
import org.azauner.ast.node.Type
import org.azauner.ast.node.scope.Scope

fun Token.getTerminalNodeFromTokenList(list: List<TerminalNode>): TerminalNode =
    list.first { it.symbol.tokenIndex == this.tokenIndex }

fun Expr.getType(scope: Scope): Type {
    if(exprEntries.isEmpty()) {

    }else {
        // has to be a
    }
}

fun OrExpr.getType(scope: Scope): Type {
    if(andExprs.isEmpty()) {
        return andExpr.getType(scope)
    }else {
        // has to be a
    }
}
