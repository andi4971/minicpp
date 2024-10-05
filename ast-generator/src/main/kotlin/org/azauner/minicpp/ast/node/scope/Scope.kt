package org.azauner.minicpp.ast.node.scope

import org.azauner.minicpp.ast.generator.exception.SemanticException
import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.ast.util.requireSemantic

class Scope(private val parent: Scope?) {

    init {
        parent?.childScopes?.add(this)
    }
    private val variables = mutableListOf<Variable>()
    private val functions: MutableList<Function> = mutableListOf()
    private val childScopes = mutableListOf<Scope>()

    fun addVariable(ident: Ident, type: ExprType, const: Boolean = false, constValue: Any? = null): Variable {
        if (variableExistsInSelf(ident)) {
            throw SemanticException("Variable $ident already exists")
        }
        val static = isGlobalScope
        return Variable(ident, type, const, static, getNextAvailableIndex(static), constValue)
            .also { variables.add(it) }
    }

    private val isGlobalScope: Boolean
        get() = parent == null

    private fun getNextAvailableIndex(static: Boolean): Int {
        if(static) {
            return -1
        }
        val nonStaticVars = variables.filterNot { it.static }
        return if (nonStaticVars.isEmpty()) {
            parent?.getNextAvailableIndex(static) ?: 0
        } else {
            nonStaticVars.maxOf { it.index } + 1
        }
    }

    fun getVariable(ident: Ident): Variable {
        return variables.find { it.ident == ident }
            ?: parent?.getVariable(ident)
            ?: throw SemanticException("Variable does not exist")
    }


    private fun variableExists(ident: Ident): Boolean {
        return variables.any { it.ident == ident }
                || parent?.variableExists(ident)
                ?: false
    }

    private fun variableExistsInSelf(ident: Ident): Boolean {
        return variables.any { it.ident == ident }
    }

    fun variableExistsInSelfOrChildren(ident: Ident): Boolean {
        return variables.any { it.ident == ident }
                || childScopes.any { it.variableExistsInSelfOrChildren(ident) }
    }

    fun checkVariableExists(ident: Ident) {
        if (!variableExists(ident)) {
            throw SemanticException("Variable $ident does not exist")
        }
    }

    fun addFunction(ident: Ident, returnType: ExprType, formParList: FormParList?, definesFunction: Boolean) {

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

    private fun tryGetFunction(ident: Ident, formParTypes: List<ExprType>): Function? {
        return functions.find { func -> func.ident == ident && func.formParTypes == formParTypes }
            ?: parent?.tryGetFunction(ident, formParTypes)
    }

    fun getFunction(ident: Ident, formParTypes: List<ExprType>): Function {
        val formParTypesAmended = formParTypes.ifEmpty {
            listOf(ExprType.VOID)
        }
        return tryGetFunction(ident, formParTypesAmended)
            ?: throw SemanticException("Function $ident with parameters $formParTypes does not exist")
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
