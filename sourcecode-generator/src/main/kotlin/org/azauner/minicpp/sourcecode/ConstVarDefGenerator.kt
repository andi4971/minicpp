package org.azauner.minicpp.sourcecode

import org.azauner.minicpp.ast.node.ConstDef
import org.azauner.minicpp.ast.node.Init
import org.azauner.minicpp.ast.node.VarDef

fun ConstDef.generateSourceCode(sb: StringBuilder) {
    sb.append("const ")
    type.generateSourceCode(sb)
    sb.append(" ")
    entries.forEachIndexed { index, entry ->
        sb.append("${entry.ident.name} = ")
        entry.value.generateSourceCode(sb)

        if (index != entries.lastIndex) {
            sb.append(", ")
        }
    }
    sb.appendLine(";")
}


fun VarDef.generateSourceCode(sb: StringBuilder) {
    type.generateSourceCode(sb)
    sb.append(" ")
    entries.forEachIndexed { index, entry ->
        if (entry.pointer) {
            sb.append("*")
        }
        sb.append("${entry.ident.name} ")
        entry.value?.let {
            sb.append(" = ")
            it.generateSourceCode(sb)
        }
        if (index != entries.lastIndex) {
            sb.append(", ")
        }
    }
    sb.appendLine(";")
}

fun Init.generateSourceCode(sb: StringBuilder) {
    this.value.generateSourceCode(sb)
}
