package org.azauner.minicpp.ast.node

import org.azauner.minicpp.ast.node.scope.Variable


data class VarDefEntry(val ident: Ident, val pointer: Boolean, val value: Init?, val variable: Variable)
