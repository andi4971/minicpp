package org.azauner.ast.node.scope

import org.azauner.ast.generator.exception.SemanticException
import org.azauner.ast.node.*
import org.azauner.ast.util.toExprType

class Scope(private val parent: Scope?) {
    private val variables = mutableListOf<Variable>()
    private val functions: MutableList<Function> = mutableListOf()

    fun addVariable(ident: Ident, type: Type, pointer: Boolean, const: Boolean = false) {
        val variable = Variable(ident, type, pointer, const)
        if (variableExists(variable)) {
            throw SemanticException("Variable ${variable.ident} already exists")
        }
        variables.add(variable)
    }

    fun getVariable(ident: Ident): Variable {
        return variables.find { it.ident == ident }
            ?: parent?.getVariable(ident)
            ?: throw SemanticException("Variable does not exist")
    }

    private fun variableExists(variable: Variable): Boolean {
        return variables.contains(variable)
                || parent?.variableExists(variable)
                ?: false
    }

    fun variableExists(ident: Ident): Boolean {
        return variables.any { it.ident == ident }
                || parent?.variableExists(ident)
                ?: false
    }

    fun checkVariableExists(ident: Ident) {
        if (!variableExists(ident)) {
            throw SemanticException("Variable $ident does not exist")
        }
    }

    fun addFunction(ident: Ident, returnType: Type, returnTypePointer: Boolean, formParList: FormParList?) {
        val function = Function(ident, returnType, returnTypePointer, (formParList?: VoidFormParListChild).toExprTypes())
        if (functionExists(function)) {
            throw SemanticException("Function already exists")
        }
        functions.add(function)
    }

    private fun functionExists(function: Function): Boolean {
        return functions.contains(function)
                || parent?.functionExists(function)
                ?: false
    }

    fun functionExists(funcHead: FuncHead): Boolean {
        val func = funcHead.run {
            Function(
                ident = ident,
                returnType = type,
                returnTypePointer = pointer,
                formParTypes = (formParList ?: VoidFormParListChild).toExprTypes()
            )
        }
        return functionExists(func)
    }

    fun getFunction(ident: Ident, formParTypes: List<ExprType>): Function {
        return functions.find { func -> func.ident == ident && func.formParTypes == formParTypes }
            ?: parent?.getFunction(ident, formParTypes)
            ?: throw SemanticException("Function does not exist")
    }


}

private fun FormParList.toExprTypes(): List<ExprType> {
    return when (this) {
        is FormParListEntries -> entries.toExprTypes()
        VoidFormParListChild -> listOf(ExprType.VOID)
    }
}

private fun List<FormParListEntry>.toExprTypes(): List<ExprType> {
    return map { entry ->
        entry.run {
            val baseType = type.toExprType()
            return@map when {
                pointer && array  && baseType == ExprType.INT -> ExprType.INT_ARR_PTR
                pointer && array  && baseType == ExprType.BOOL -> ExprType.BOOL_ARR_PTR
                pointer && baseType == ExprType.INT -> ExprType.INT_PTR
                pointer && baseType == ExprType.BOOL -> ExprType.BOOL_PTR
                else -> if(baseType == ExprType.VOID) {
                    throw SemanticException("cannot use void for multiple parameters")
                }else {
                    baseType
                }
            }
        }
    }
}
