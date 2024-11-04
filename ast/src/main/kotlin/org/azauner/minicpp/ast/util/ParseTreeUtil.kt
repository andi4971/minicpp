package org.azauner.minicpp.ast.util

import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.minicpp.ast.generator.exception.SemanticException
import org.azauner.minicpp.ast.node.ARR_TYPES
import org.azauner.minicpp.ast.node.ArrayAccessOperation
import org.azauner.minicpp.ast.node.ExprType
import org.azauner.minicpp.ast.node.INIT_TYPES_NOT_NULL

fun Token.getTerminalNodeFromTokenList(list: List<TerminalNode>): TerminalNode =
    list.first { it.symbol.tokenIndex == this.tokenIndex }

fun org.azauner.minicpp.ast.node.Expr.validate() {
    this.getType()
}

fun org.azauner.minicpp.ast.node.Expr.getType(): org.azauner.minicpp.ast.node.ExprType {
    val firstType = firstExpr.getType()
    if (exprEntries.isNotEmpty()) {
        if (firstType in org.azauner.minicpp.ast.node.ARR_TYPES) {
            requireSemantic(exprEntries.dropLast(1).all { it.orExpr.getType() in ARR_TYPES }) {
                "All expressions have to be of type array"
            }
            exprEntries.last().let {
                val type = it.orExpr.getType()
                requireSemantic(type in ARR_TYPES || type == ExprType.NULLPTR) {
                    "Last expression has to be array or nullptr"
                }
            }
        } else {
            requireSemantic(exprEntries.all { it.orExpr.getType() == firstType }) {
                "All expressions have to be of type $firstType"
            }
        }
        val entries = this.mapToAssignPairs()
        entries.forEach { (orExpr, assignOperator) ->
            if (assignOperator != null) {
                val variable = scope.getVariable(orExpr.getIdent())
                requireSemantic(!variable.const) {
                    "Cannot assign to const variable"
                }
            }
        }
    }
    return firstType
}

fun org.azauner.minicpp.ast.node.Expr.mapToAssignPairs(): List<Pair<org.azauner.minicpp.ast.node.OrExpr, org.azauner.minicpp.ast.node.AssignOperator?>> {
    return (listOf(firstExpr) + exprEntries.map { it.orExpr })
        .mapIndexed { index, entry ->
            entry to exprEntries.getOrNull(index)?.assignOperator
        }

}

fun org.azauner.minicpp.ast.node.OrExpr.getIdent(): org.azauner.minicpp.ast.node.Ident {
    this.andExpressions.first().relExpressions.first().firstExpr.term.firstNotFact.fact.let {
        return when (it) {
            is org.azauner.minicpp.ast.node.ActionFact -> it.ident
            else -> throw SemanticException("could not find ident")
        }
    }
}

fun org.azauner.minicpp.ast.node.OrExpr.getType(): org.azauner.minicpp.ast.node.ExprType {
    return if (andExpressions.size > 1) {
        andExpressions.forEach {
            requireSemantic(it.getType() == ExprType.BOOL) {
                "required all expressions to be type bool but found one with ${it.getType()}"
            }
        }

        org.azauner.minicpp.ast.node.ExprType.BOOL
    } else {
        andExpressions.first().getType()
    }
}

private fun org.azauner.minicpp.ast.node.AndExpr.getType(): org.azauner.minicpp.ast.node.ExprType {
    return if (relExpressions.size > 1) {
        requireSemantic(relExpressions.all { it.getType() == ExprType.BOOL }) {
            "All rel expressions have to be of type bool"
        }
        org.azauner.minicpp.ast.node.ExprType.BOOL
    } else {
        relExpressions.first().getType()
    }
}

private fun org.azauner.minicpp.ast.node.RelExpr.getType(): org.azauner.minicpp.ast.node.ExprType {
    val firstType = firstExpr.getType()
    return if (relExprEntries.isNotEmpty()) {
        val firstEntry = relExprEntries.first()
        validateRelExpOperatorTypes(firstType, firstEntry.relOperator, firstEntry.simpleExpr.getType())
        relExprEntries.drop(1).forEach {
            requireSemantic(it.simpleExpr.getType() in INIT_TYPES_NOT_NULL) {
                "All simple expressions have to be of type bool or int"
            }
        }
        org.azauner.minicpp.ast.node.ExprType.BOOL
    } else {
        firstType
    }
}

private fun validateRelExpOperatorTypes(
    leftType: org.azauner.minicpp.ast.node.ExprType,
    operator: org.azauner.minicpp.ast.node.RelOperator,
    rightType: org.azauner.minicpp.ast.node.ExprType
) {
    requireSemantic(leftType == rightType) {
        "Left and right type have to be the same"
    }
    if (operator !in org.azauner.minicpp.ast.node.boolRelOperators) {
        requireSemantic(leftType == ExprType.INT) {
            "Left and right type have to be int"
        }
    }
}

private fun org.azauner.minicpp.ast.node.SimpleExpr.getType(): org.azauner.minicpp.ast.node.ExprType {
    val firstType = term.getType()
    if (sign != null) {
        requireSemantic(firstType == ExprType.INT) {
            "Sign can only be used with int"
        }
    }
    if (simpleExprEntries.isNotEmpty()) {
        requireSemantic(simpleExprEntries.all { it.term.getType() == ExprType.INT }) {
            "All simple expr entries have to be of type int"
        }
        requireSemantic(firstType == ExprType.INT) {
            "First type has to be int"
        }
    }
    return firstType
}

private fun org.azauner.minicpp.ast.node.Term.getType(): org.azauner.minicpp.ast.node.ExprType {
    val firstType = firstNotFact.getType()
    if (termEntries.isNotEmpty()) {
        requireSemantic(termEntries.all { it.notFact.getType() == ExprType.INT }) {
            "All term entries have to be of type int"
        }
    }
    return firstType
}

private fun org.azauner.minicpp.ast.node.NotFact.getType(): org.azauner.minicpp.ast.node.ExprType {
    val type = fact.getType()
    if (negated) {
        requireSemantic(type == ExprType.BOOL) {
            "Negated fact has to be of type bool"
        }
    }
    return type
}

private fun org.azauner.minicpp.ast.node.Fact.getType(): org.azauner.minicpp.ast.node.ExprType {
    return when (this) {
        is org.azauner.minicpp.ast.node.BoolType -> org.azauner.minicpp.ast.node.ExprType.BOOL
        is org.azauner.minicpp.ast.node.ExprFact -> expr.getType()
        is org.azauner.minicpp.ast.node.NewArrayTypeFact -> {
            requireSemantic(type in INIT_TYPES_NOT_NULL) {
                "cannot create array of type $type"
            }
            type.toArrayType()
        }

        is org.azauner.minicpp.ast.node.ActionFact -> this.getType()
        is org.azauner.minicpp.ast.node.IntType -> org.azauner.minicpp.ast.node.ExprType.INT
        org.azauner.minicpp.ast.node.NullPtrType -> org.azauner.minicpp.ast.node.ExprType.NULLPTR
    }
}

private fun org.azauner.minicpp.ast.node.ActionFact.getType(): org.azauner.minicpp.ast.node.ExprType {
    if (prefix != null || suffix != null) {
        requireSemantic(actionOp == null || actionOp is ArrayAccessOperation) {
            "Cannot use prefix or suffix with function call"
        }
        val variableType = scope.getVariable(ident).type
        requireSemantic(variableType == ExprType.INT || (variableType == ExprType.INT_ARR && actionOp is ArrayAccessOperation)) {
            "Variable $ident has to be of type int or int array to use prefix or suffix"
        }
    }
    return actionOp?.getType(ident) ?: scope.getVariable(ident).type
}

private fun org.azauner.minicpp.ast.node.ActionOperation.getType(ident: org.azauner.minicpp.ast.node.Ident): org.azauner.minicpp.ast.node.ExprType {
    return when (this) {
        is org.azauner.minicpp.ast.node.ArrayAccessOperation -> {
            val variable = scope.getVariable(ident)
            requireSemantic(variable.type in ARR_TYPES) {
                "Variable $ident is not a pointer"
            }
            requireSemantic(expr.getType() == ExprType.INT) {
                "Array index has to be of type int"
            }
            variable.type.toNonArrayType()
        }

        is org.azauner.minicpp.ast.node.CallOperation -> {
            val parameters = actParList.map { it.getType() }
            val function = scope.getFunction(ident, parameters)
            function.returnType
        }
    }
}

fun org.azauner.minicpp.ast.node.ExprType.toArrayType(): org.azauner.minicpp.ast.node.ExprType {
    return when (this) {
        org.azauner.minicpp.ast.node.ExprType.INT -> org.azauner.minicpp.ast.node.ExprType.INT_ARR
        org.azauner.minicpp.ast.node.ExprType.BOOL -> org.azauner.minicpp.ast.node.ExprType.BOOL_ARR
        else -> throw SemanticException("Type $this cannot be a pointer")
    }
}

fun org.azauner.minicpp.ast.node.ExprType.toNonArrayType(): org.azauner.minicpp.ast.node.ExprType {
    return when (this) {
        org.azauner.minicpp.ast.node.ExprType.INT_ARR -> org.azauner.minicpp.ast.node.ExprType.INT
        org.azauner.minicpp.ast.node.ExprType.BOOL_ARR -> org.azauner.minicpp.ast.node.ExprType.BOOL
        else -> throw SemanticException("Type $this cannot be a non array type")
    }
}

fun org.azauner.minicpp.ast.node.DataType.getExprType(): org.azauner.minicpp.ast.node.ExprType {
    return when (this) {
        is org.azauner.minicpp.ast.node.BoolType -> org.azauner.minicpp.ast.node.ExprType.BOOL
        is org.azauner.minicpp.ast.node.IntType -> org.azauner.minicpp.ast.node.ExprType.INT
        org.azauner.minicpp.ast.node.NullPtrType -> org.azauner.minicpp.ast.node.ExprType.NULLPTR
    }
}

fun org.azauner.minicpp.ast.node.ExprType.toPointerTypeOptional(toPointer: Boolean): org.azauner.minicpp.ast.node.ExprType {
    return if (toPointer) {
        this.toArrayType()
    } else {
        this
    }
}
