package org.azauner.ast.generator.visitor.util

import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.ast.node.*
import org.azauner.ast.node.scope.Scope
import kotlin.math.E

fun Token.getTerminalNodeFromTokenList(list: List<TerminalNode>): TerminalNode =
    list.first { it.symbol.tokenIndex == this.tokenIndex }

fun Expr.getType(scope: Scope): ExprType {
    if(exprEntries.isEmpty()) {

    }else {
        // has to be a
    }
}

fun OrExpr.getType(scope: Scope): ExprType {
    andExpressions.forEach { it.getType(scope) }
}

private fun AndExpr.getType(scope: Scope) {
    this.relExpressions.forEach { it.getType(scope) }
}

private fun RelExpr.getType(scope: Scope): ExprType {
    firstExpr.getType(scope)
}

private fun SimpleExpr.getType(scope: Scope): ExprType {
    term.getType(scope)
}

private fun Term.getType(scope: Scope) {
    firstNotFact.getType(scope)
}

private fun NotFact.getType(scope: Scope) {
    fact.getType(scope)
}

private fun Fact.getType(scope: Scope): ExprType {
    return when(this) {
        is BoolType -> ExprType.BOOL
        is ExprFact -> expr.getType(scope)
        is NewArrayTypeFact -> when(type) {
            Type.VOID -> throw IllegalStateException("Cannot create an array of void")
            Type.BOOL -> ExprType.BOOL_ARR
            Type.INT -> ExprType.INT_ARR
        }
        is ActionFact -> this.getType(scope)
        is IntType -> ExprType.INT
        NullPtrType -> ExprType.NULLPTR
    }
}

private fun ActionFact.getType(scope: Scope): ExprType {
    return actionOp?.getType(scope, ident) ?: scope.getVariable(ident).type.toExprType()

}

private fun ActionOperation.getType(scope: Scope, ident: Ident): ExprType {
    return when(this) {
        //when array access variable needs to be pointer
        is ArrayAccessOperation -> {
            val variable = scope.getVariable(ident)
            if(variable.pointer) {
                variable.type.toExprType()
            } else {
                throw IllegalStateException("Variable $ident is not a pointer")
            }
        }
        is CallOperation -> {
            //todo get parameter types of function call

            val function = scope.getFunction(ident)
            function.returnType.toExprType()
        }
    }
}

private fun Type.toExprType(): ExprType {
    return when(this) {
        Type.BOOL -> ExprType.BOOL
        Type.INT -> ExprType.INT
        Type.VOID -> throw IllegalStateException("Cannot create an expr type of void")
    }
}
