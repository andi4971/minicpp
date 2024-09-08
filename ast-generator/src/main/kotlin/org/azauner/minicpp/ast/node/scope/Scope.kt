package org.azauner.minicpp.ast.node.scope

import org.azauner.minicpp.ast.generator.exception.SemanticException
import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.ast.util.requireSemantic

class Scope(private val parent: Scope?) {
    private val variables = mutableListOf<Variable>()
    private val functions: MutableList<Function> = mutableListOf()

    fun addVariable(ident: Ident, type: ExprType, const: Boolean = false) {
        val variable = Variable(ident, type, const)
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

    fun addFunction(ident: Ident,returnType: ExprType, formParList: FormParList?, definesFunction: Boolean) {

        val function = tryGetFunction(ident, formParList.toExprTypes())

        when {
            function != null && function.isDefined -> {
                throw SemanticException("Function already defined")
            }
            function != null -> {
                requireSemantic(definesFunction) {
                    "Function already declared"
                }
                function.isDefined = true
            }
            else -> {
                functions.add(Function(ident, returnType, formParList.toExprTypes(), definesFunction))
            }
        }
    }

    fun functionExists(funcHead: FuncHead): Boolean {
        return tryGetFunction(
            funcHead.ident,
            (funcHead.formParList ?: VoidFormParListChild).toExprTypes()
        ) != null
    }

    private fun tryGetFunction(ident: Ident, formParTypes: List<ExprType>): Function? {
        return functions.find { func -> func.ident == ident && func.formParTypes == formParTypes }
            ?: parent?.tryGetFunction(ident, formParTypes)
    }

    fun getFunction(ident: Ident, formParTypes: List<ExprType>): Function {
        val formParTypesAmended = formParTypes.ifEmpty {
            listOf(ExprType.VOID)
        }
        return tryGetFunction(ident, formParTypesAmended)
            ?: throw SemanticException("Function does not exist")
    }

}

private fun List<FormParListEntry>.toExprTypes(): List<ExprType> {
    return map { entry -> entry.type }
}
private fun FormParList?.toExprTypes(): List<ExprType> {
    return when (this) {
        is FormParListEntries -> entries.toExprTypes()
        else -> listOf(ExprType.VOID)
    }
}
