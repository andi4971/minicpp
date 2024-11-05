package org.azauner.minicpp.ast.util

class ScopeHandler {

    private val scopes = mutableListOf<org.azauner.minicpp.ast.node.scope.Scope>()

    init {
        scopes.add(org.azauner.minicpp.ast.node.scope.Scope(null))
    }


    fun getScope(): org.azauner.minicpp.ast.node.scope.Scope {
        return scopes.last()
    }

    fun popScope() {
        scopes.removeLast()
    }

    fun pushChildScope() {
        scopes.add(org.azauner.minicpp.ast.node.scope.Scope(getScope()))
    }
}
