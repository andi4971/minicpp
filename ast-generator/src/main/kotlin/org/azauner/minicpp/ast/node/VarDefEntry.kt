package org.azauner.minicpp.ast.node


data class VarDefEntry(val ident: Ident, val pointer: Boolean, val value: Init?)
