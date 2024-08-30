package org.azauner.ast.util

import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.ast.generator.exception.SemanticException
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
    if (operator == AssignOperator.ASSIGN) {
          requireSemantic(firstType == secondType) {
                "First and second type have to be the same"
        }
    } else {
        requireSemantic(firstType == ExprType.INT && secondType == ExprType.INT) {
            "First and second type have to be int"
        }
    }
}

fun OrExpr.getType(scope: Scope): ExprType {
    return if (andExpressions.size > 1) {
        andExpressions.forEach {
            requireSemantic(it.getType(scope) == ExprType.BOOL) {
                "required all expressions to be type bool but found one with ${it.getType(scope)}"
            }
        }

        ExprType.BOOL
    } else {
        andExpressions.first().getType(scope)
    }
}

private fun AndExpr.getType(scope: Scope): ExprType {
    return if (relExpressions.size > 1) {
        requireSemantic(relExpressions.all { it.getType(scope) == ExprType.BOOL }) {
            "All rel expressions have to be of type bool"
        }
        ExprType.BOOL
    } else {
        relExpressions.first().getType(scope)
    }
}

private fun RelExpr.getType(scope: Scope): ExprType {
    val firstType = firstExpr.getType(scope)
    return if (relExprEntries.isNotEmpty()) {
        val firstEntry = relExprEntries.first()
        validateRelExpOperatorTypes(firstType, firstEntry.relOperator, firstEntry.simpleExpr.getType(scope))
        relExprEntries.drop(1).forEach {
            requireSemantic(it.simpleExpr.getType(scope) == ExprType.BOOL) {
                "All simple expressions have to be of type bool"
            }
        }
        ExprType.BOOL
    } else {
        firstType
    }
}

private fun validateRelExpOperatorTypes(leftType: ExprType, operator: RelOperator, rightType: ExprType) {
    requireSemantic(leftType == rightType) {
        "Left and right type have to be the same"
    }
    if (operator !in boolRelOperators) {
        requireSemantic(leftType == ExprType.INT) {
            "Left and right type have to be int"
        }
    }
}

private fun SimpleExpr.getType(scope: Scope): ExprType {
    val firstType = term.getType(scope)
    if (sign != null) {
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
    val type = fact.getType(scope)
    if (negated) {
        requireSemantic(type == ExprType.BOOL) {
            "Negated fact has to be of type bool"
        }
    }
    return type
}

private fun Fact.getType(scope: Scope): ExprType {
    return when (this) {
        is BoolType -> ExprType.BOOL
        is ExprFact -> expr.getType(scope)
        is NewArrayTypeFact -> {
            requireSemantic(type in NON_POINTER_INIT_TYPES) {
                "cannot create array of type $type"
            }
            type.toArrayType()
        }
        is ActionFact -> this.getType(scope)
        is IntType -> ExprType.INT
        NullPtrType -> ExprType.NULLPTR
    }
}

private fun ActionFact.getType(scope: Scope): ExprType {
    if(prefix != null || suffix != null) {
        requireSemantic(actionOp == null) {
            "required variable"
        }
        val variableType = scope.getVariable(ident).type
        requireSemantic(variableType == ExprType.INT) {
            "Variable $ident has to be of type int"
        }
    }
    return actionOp?.getType(scope, ident) ?: scope.getVariable(ident).type
}

private fun ActionOperation.getType(scope: Scope, ident: Ident): ExprType {
    return when (this) {
        is ArrayAccessOperation -> {
            val variable = scope.getVariable(ident)
            requireSemantic(variable.type in ARR_TYPES) {
                "Variable $ident is not a pointer"
            }
            requireSemantic(expr.getType(scope) == ExprType.INT) {
                "Array index has to be of type int"
            }
            variable.type.toNonArrayType()
        }

        is CallOperation -> {
            val parameters = actParList.map { it.getType(scope) }
            val function = scope.getFunction(ident, parameters)
            function.returnType
        }
    }
}

fun ExprType.toArrayType(): ExprType {
   return when (this) {
        ExprType.INT -> ExprType.INT_ARR
        ExprType.BOOL -> ExprType.BOOL_ARR
        else -> throw SemanticException("Type $this cannot be a pointer")
    }
}

fun ExprType.toNonArrayType(): ExprType {
    return when (this) {
        ExprType.INT_ARR -> ExprType.INT
        ExprType.BOOL_ARR -> ExprType.BOOL
        else -> throw SemanticException("Type $this cannot be a non array type")
    }
}

fun DataType.getExprType(): ExprType {
    return when (this) {
        is BoolType -> ExprType.BOOL
        is IntType -> ExprType.INT
        NullPtrType -> ExprType.NULLPTR
    }
}

fun ExprType.toPointerTypeOptional(toPointer: Boolean): ExprType {
    return if (toPointer) {
        this.toArrayType()
    } else {
        this
    }
}
