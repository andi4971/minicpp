package org.azauner.ast.util

import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.ast.node.*
import org.azauner.ast.node.scope.Scope

fun Token.getTerminalNodeFromTokenList(list: List<TerminalNode>): TerminalNode =
    list.first { it.symbol.tokenIndex == this.tokenIndex }

fun Expr.validate(scope: Scope) {
    this.getType(scope)
}

fun Expr.getType(scope: Scope): ExprType {
    val firstType = firstExpr.getType(scope)
    return if (exprEntries.isNotEmpty()) {
        val firstEntry = exprEntries.first()
        validateExprEntry(firstType, firstEntry.assignOperator, firstEntry.orExpr.getType(scope))
        //todo check how this should work
        exprEntries.drop(1).forEach {
            requireSemantic(it.orExpr.getType(scope) == ExprType.BOOL) {
                "All or expressions have to be of type bool"
            }
        }
        ExprType.BOOL
    } else {
        return firstType
    }
}

fun validateExprEntry(firstType: ExprType, operator: AssignOperator, secondType: ExprType) {
    if(operator == AssignOperator.ASSIGN) {
        requireSemantic(firstType == secondType) {
            "First and second type have to be the same"
        }
    }else {
        requireSemantic(firstType == ExprType.INT && secondType == ExprType.INT) {
            "First and second type have to be int"
        }
    }
}

fun OrExpr.getType(scope: Scope): ExprType {
    return if(andExpressions.size > 1) {
        andExpressions.forEach {
            requireSemantic(it.getType(scope) == ExprType.BOOL) {
                "required all expressions to be type bool but found one with ${it.getType(scope)}"
            }
        }

        ExprType.BOOL
    }else {
        andExpressions.first().getType(scope)
    }
}

private fun AndExpr.getType(scope: Scope): ExprType {
    return if(relExpressions.size > 1) {
        requireSemantic(relExpressions.all { it.getType(scope) == ExprType.BOOL }) {
            "All rel expressions have to be of type bool"
        }
        ExprType.BOOL
    }else {
        relExpressions.first().getType(scope)
    }
}

private fun RelExpr.getType(scope: Scope): ExprType {
   val firstType =  firstExpr.getType(scope)
    return if(relExprEntries.isNotEmpty()) {
        val firstEntry = relExprEntries.first()
        validateRelExpOperatorTypes(firstType,firstEntry.relOperator,firstEntry.simpleExpr.getType(scope))
        relExprEntries.drop(1).forEach {
            requireSemantic(it.simpleExpr.getType(scope) == ExprType.BOOL) {
                "All simple expressions have to be of type bool"
            }
        }
        ExprType.BOOL
    }else {
        firstType
    }
}

private fun validateRelExpOperatorTypes(leftType: ExprType, operator: RelOperator, rightType: ExprType) {
    requireSemantic(leftType == rightType) {
        "Left and right type have to be the same"
    }
   if(operator !in boolRelOperators) {
       requireSemantic(leftType == ExprType.INT) {
           "Left and right type have to be int"
       }
   }
}

private fun SimpleExpr.getType(scope: Scope): ExprType {
    val firstType = term.getType(scope)
    if(sign!= null) {
        requireSemantic(firstType == ExprType.INT) {
            "Sign can only be used with int"
        }
    }
    if (simpleExprEntries.isNotEmpty()) {
        requireSemantic(simpleExprEntries.all { it.term.getType(scope) == ExprType.INT }) {
            "All simple expr entries have to be of type int"
        }
        requireSemantic(firstType == ExprType.INT) {
            "First type has to be int"
        }
    }
    return firstType
}

private fun Term.getType(scope: Scope): ExprType {
    val firstType = firstNotFact.getType(scope)
    if (termEntries.isNotEmpty()) {
        requireSemantic(termEntries.all { it.notFact.getType(scope) == ExprType.INT }) {
            "All term entries have to be of type int"
        }
    }
    return firstType
}

private fun NotFact.getType(scope: Scope): ExprType {
    return fact.getType(scope)
}

private fun Fact.getType(scope: Scope): ExprType {
    return when (this) {
        is BoolType -> ExprType.BOOL
        is ExprFact -> expr.getType(scope)
        is NewArrayTypeFact -> when (type) {
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
    return when (this) {
        //when array access variable needs to be pointer
        is ArrayAccessOperation -> {
            val variable = scope.getVariable(ident)
            if (variable.pointer) {
                variable.type.toExprType()
            } else {
                throw IllegalStateException("Variable $ident is not a pointer")
            }
        }

        is CallOperation -> {
            val parameters = actParList.map { it.getType(scope) }
            val function = scope.getFunction(ident, parameters)
            function.returnType.toExprType()
        }
    }
}

fun Type.toExprType(): ExprType {
    return when (this) {
        Type.BOOL -> ExprType.BOOL
        Type.INT -> ExprType.INT
        Type.VOID -> ExprType.VOID
    }
}
