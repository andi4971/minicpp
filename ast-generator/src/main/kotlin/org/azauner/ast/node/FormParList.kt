package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

sealed interface FormParList : SourceCodeGenerator

data object VoidFormParListChild : FormParList {
    override fun generateSourceCode(sb: StringBuilder) {
        sb.append("void")
    }
}

data class FormParListEntries(val entries: List<FormParListEntry>) : FormParList {
    override fun generateSourceCode(sb: StringBuilder) {
        entries.forEachIndexed { index, formParListEntry ->
            formParListEntry.type.generateSourceCode(sb)
            sb.append(" ")
            sb.append(formParListEntry.ident.name)
            if (index != entries.lastIndex) {
                sb.append(", ")
            }
        }
    }
}

data class FormParListEntry(val type: ExprType, val ident: Ident)

