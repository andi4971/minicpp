package org.azauner.minicpp.ast.util

import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.minicpp.ast.generator.exception.SemanticException
import org.azauner.minicpp.ast.node.*

fun Token.getTerminalNodeFromTokenList(list: List<TerminalNode>): TerminalNode =
    list.first { it.symbol.tokenIndex == this.tokenIndex }

fun Expr.validate() {
    this.getType()
}

fun Expr.getType(): ExprType {
    val firstType = firstExpr.getType()
    if (exprEntries.isNotEmpty()) {
        if (firstType in ARR_TYPES) {
            requireSemantic(exprEntries.dropLast(1).all { it.orExpr.getType() in ARR_TYPES }) {
                "All expressions have to be of type array"
            }
            exprEntries.last().let {
                val type = it.orExpr.getType()
                requireSemantic(type in ARR_TYPES || type == ExprType.NULLPTR) {
                    "Last expression has to be array or nullptr"
                }
            }
        }else {
            requireSemantic(exprEntries.all { it.orExpr.getType() == firstType }) {
                "All expressions have to be of type $firstType"
            }
        }
        val entries = this.mapToAssignPairs()
        entries.forEach { (orExpr, assignOperator) ->
            if(assignOperator != null) {
                val variable = scope.getVariable(orExpr.getIdent())
                requireSemantic(!variable.const){
                    "Cannot assign to const variable"
                }
            }
        }
    }
    return firstType
}

fun Expr.mapToAssignPairs(): List<Pair<OrExpr, AssignOperator?>> {
    return (listOf(firstExpr) + exprEntries.map { it.orExpr })
        .mapIndexed { index, entry ->
            entry to exprEntries.getOrNull(index)?.assignOperator
        }

}

fun OrExpr.getIdent(): Ident {
    this.andExpressions.first().relExpressions.first().firstExpr.term.firstNotFact.fact.let {
        return when (it) {
            is ActionFact -> it.ident
            else -> throw SemanticException("could not find ident")
        }
    }
}

fun OrExpr.getType(): ExprType {
    return if (andExpressions.size > 1) {
        andExpressions.forEach {
            requireSemantic(it.getType() == ExprType.BOOL) {
                "required all expressions to be type bool but found one with ${it.getType()}"
            }
        }

        ExprType.BOOL
    } else {
        andExpressions.first().getType()
    }
}

private fun AndExpr.getType(): ExprType {
    return if (relExpressions.size > 1) {
        requireSemantic(relExpressions.all { it.getType() == ExprType.BOOL }) {
            "All rel expressions have to be of type bool"
        }
        ExprType.BOOL
    } else {
        relExpressions.first().getType()
    }
}

private fun RelExpr.getType(): ExprType {
    val firstType = firstExpr.getType()
    return if (relExprEntries.isNotEmpty()) {
        val firstEntry = relExprEntries.first()
        validateRelExpOperatorTypes(firstType, firstEntry.relOperator, firstEntry.simpleExpr.getType())
        relExprEntries.drop(1).forEach {
            requireSemantic(it.simpleExpr.getType() in INIT_TYPES_NOT_NULL) {
                "All simple expressions have to be of type bool or int"
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

private fun SimpleExpr.getType(): ExprType {
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

private fun Term.getType(): ExprType {
    val firstType = firstNotFact.getType()
    if (termEntries.isNotEmpty()) {
        requireSemantic(termEntries.all { it.notFact.getType() == ExprType.INT }) {
            "All term entries have to be of type int"
        }
    }
    return firstType
}

private fun NotFact.getType(): ExprType {
    val type = fact.getType()
    if (negated) {
        requireSemantic(type == ExprType.BOOL) {
            "Negated fact has to be of type bool"
        }
    }
    return type
}

private fun Fact.getType(): ExprType {
    return when (this) {
        is BoolType -> ExprType.BOOL
        is ExprFact -> expr.getType()
        is NewArrayTypeFact -> {
            requireSemantic(type in INIT_TYPES_NOT_NULL) {
                "cannot create array of type $type"
            }
            type.toArrayType()
        }

        is ActionFact -> this.getType()
        is IntType -> ExprType.INT
        NullPtrType -> ExprType.NULLPTR
    }
}

private fun ActionFact.getType(): ExprType {
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

private fun ActionOperation.getType(ident: Ident): ExprType {
    return when (this) {
        is ArrayAccessOperation -> {
            val variable = scope.getVariable(ident)
            requireSemantic(variable.type in ARR_TYPES) {
                "Variable $ident is not a pointer"
            }
            requireSemantic(expr.getType() == ExprType.INT) {
                "Array index has to be of type int"
            }
            variable.type.toNonArrayType()
        }

        is CallOperation -> {
            val parameters = actParList.map { it.getType() }
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
