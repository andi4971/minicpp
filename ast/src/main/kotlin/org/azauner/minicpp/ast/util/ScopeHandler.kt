package org.azauner.minicpp.ast.util

import org.azauner.minicpp.ast.node.scope.Scope

class ScopeHandler {

    private val scopes = mutableListOf<Scope>()

    init {
        scopes.add(Scope(null))
    }

    fun getScope(): Scope {
        return scopes.last()
    }

    fun popScope() {
        scopes.removeLast()
    }

    fun pushChildScope() {
        scopes.add(Scope(getScope()))
    }
}
