package org.azauner.minicpp.ast.node

sealed interface FormParList

data object VoidFormParListChild : org.azauner.minicpp.ast.node.FormParList

data class FormParListEntries(val entries: List<org.azauner.minicpp.ast.node.FormParListEntry>) :
    org.azauner.minicpp.ast.node.FormParList

data class FormParListEntry(
    val type: org.azauner.minicpp.ast.node.ExprType,
    val ident: org.azauner.minicpp.ast.node.Ident
)

