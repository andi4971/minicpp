package org.azauner.ast.node

sealed interface FormParList

data object VoidFormParListChild: FormParList

data class FormParListEntries(val entries: List<FormParListEntry>): FormParList

data class FormParListEntry(val type: ExprType, val ident: Ident)

