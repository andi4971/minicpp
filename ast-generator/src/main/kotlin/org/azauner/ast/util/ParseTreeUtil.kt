package org.azauner.ast.util

import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.ast.node.*
import org.azauner.ast.node.scope.Scope

fun Token.getTerminalNodeFromTokenList(list: List<TerminalNode>): TerminalNode =
    list.first { it.symbol.tokenIndex == this.tokenIndex }

fun Expr.getType(scope: Scope): ExprType {
    val firstType = firstExpr.getType(scope)
    return if (exprEntries.isNotEmpty()) {
        val firstEntry = exprEntries.first()
        validateExprEntry(firstType, firstEntry.assignOperator, firstEntry.orExpr.getType(scope))
        //todo check how this should work
        exprEntries.drop(1).forEach {
            require(it.orExpr.getType(scope) == ExprType.BOOL)
        }
        ExprType.BOOL
    } else {
        return firstType
    }
}

fun validateExprEntry(firstType: ExprType, operator: AssignOperator, secondType: ExprType) {
    if(operator == AssignOperator.ASSIGN) {
        require(firstType == secondType) {
            "First and second type have to be the same"
        }
    }else {
        require(firstType == ExprType.INT && secondType == ExprType.INT) {
            "First and second type have to be int"
        }
    }
}

fun OrExpr.getType(scope: Scope): ExprType {
    return if(andExpressions.size > 1) {
        require(andExpressions.all { it.getType(scope) == ExprType.BOOL }) {
            "All and expressions have to be of type bool"
        }
        ExprType.BOOL
    }else {
        andExpressions.first().getType(scope)
    }
}

private fun AndExpr.getType(scope: Scope): ExprType {
    return if(relExpressions.size > 1) {
        require(relExpressions.all { it.getType(scope) == ExprType.BOOL }) {
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
            require(it.simpleExpr.getType(scope) == ExprType.BOOL)
        }
        ExprType.BOOL
    }else {
        firstType
    }
}

private fun validateRelExpOperatorTypes(leftType: ExprType, operator: RelOperator, rightType: ExprType) {
    require(leftType == rightType) {
        "Left and right type have to be the same"
    }
   if(operator !in boolRelOperators) {
       require(leftType == ExprType.INT) {
           "Left and right type have to be int"
       }
   }
}

private fun SimpleExpr.getType(scope: Scope): ExprType {
    val firstType = term.getType(scope)
    if(sign!= null) {
        require(firstType == ExprType.INT) {
            "Sign can only be used with int"
        }
    }
    if (simpleExprEntries.isNotEmpty()) {
        require(simpleExprEntries.all { it.term.getType(scope) == ExprType.INT }) {
            "All simple expr entries have to be of type int"
        }
        require(firstType == ExprType.INT) {
            "First type has to be int"
        }
    }
    return firstType
}

private fun Term.getType(scope: Scope): ExprType {
    val firstType = firstNotFact.getType(scope)
    if (termEntries.isNotEmpty()) {
        require(termEntries.all { it.notFact.getType(scope) == ExprType.INT }) {
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
        Type.VOID -> throw IllegalStateException("Cannot create an expr type of void")
    }
}
