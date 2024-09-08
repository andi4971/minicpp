package org.azauner.ast.node


data class VarDefEntry(val ident: Ident, val pointer: Boolean, val value: Init?)
