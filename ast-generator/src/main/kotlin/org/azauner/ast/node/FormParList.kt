package org.azauner.ast.node

sealed interface FormParList

data object VoidFormParListChild: FormParList

data class FormParListEntries(val entries: List<FormParListEntry>): FormParList

data class FormParListEntry(val type: Type, val pointer: Boolean, val ident: Ident, val array: Boolean)

