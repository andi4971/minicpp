package org.azauner.ast.node.func

import org.azauner.ast.node.Type

data class FormParList (val child: FormParListChild)

sealed class FormParListChild

data object VoidFormParListChild: FormParListChild()

data class FormParListEntries(val entries: List<FormParListEntry>): FormParListChild()

data class FormParListEntry(val type: Type, val star: Boolean, val ident: String, val array: Boolean)

