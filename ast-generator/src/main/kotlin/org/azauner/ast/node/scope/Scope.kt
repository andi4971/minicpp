package org.azauner.ast.node.scope

import org.azauner.ast.node.*

class Scope(private val parent: Scope?) {
    private val variables = mutableListOf<Variable>()
    private val functions: MutableList<Function> = mutableListOf()

    fun addVariable(ident: Ident, type: Type, pointer: Boolean, const: Boolean = false) {
        val variable = Variable(ident, type, pointer, const)
        if (variableExists(variable)) {
            throw Exception("Variable already exists")
        }
        variables.add(variable)
    }

    fun getVariable(ident: Ident): Variable {
        return variables.find { it.ident == ident }
            ?:
            parent?.getVariable(ident)
                ?: throw Exception("Variable does not exist")
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
        if(!variableExists(ident)) {
            throw Exception("Variable $ident does not exist")
        }
    }

    fun addFunction(ident: Ident, returnType: Type, returnTypePointer: Boolean, formParList: FormParList?) {
        val function = Function(ident, returnType, returnTypePointer, formParList)
        if (functionExists(function)) {
            throw Exception("Function already exists")
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
                formParList = formParList
            )
        }
        return functionExists(func)
    }

    fun getFunction(ident: Ident, returnType: Type, returnTypePointer: Boolean, formParList: FormParList?): Function {
        val function = Function(ident, returnType, returnTypePointer, formParList)
        return if (functionExists(function)) {
            function
        } else {
            throw Exception("Function does not exist")
        }
    }



    fun checkFunctionExists(ident: Ident, actParList: List<Expr>) {

    }

}
