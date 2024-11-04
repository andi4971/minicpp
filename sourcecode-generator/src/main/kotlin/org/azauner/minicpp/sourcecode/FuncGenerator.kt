package org.azauner.minicpp.sourcecode

fun org.azauner.minicpp.ast.node.FuncDecl.generateSourceCode(sb: StringBuilder) {
    funcHead.generateSourceCode(sb)
    sb.appendLine(";")
    sb.appendLine()
}

fun org.azauner.minicpp.ast.node.FuncDef.generateSourceCode(sb: StringBuilder) {
    funHead.generateSourceCode(sb)
    sb.appendLine()
    block.generateSourceCode(sb)
}

fun org.azauner.minicpp.ast.node.FuncHead.generateSourceCode(sb: StringBuilder) {
    type.generateSourceCode(sb)
    sb.append(" ")
    sb.append(ident.name)
    sb.append("(")
    formParList?.let {
        when (it) {
            is org.azauner.minicpp.ast.node.FormParListEntries -> it.generateSourceCode(sb)
            is org.azauner.minicpp.ast.node.VoidFormParListChild -> it.generateSourceCode(sb)
        }
    }
    sb.append(")")
}


fun org.azauner.minicpp.ast.node.FormParListEntries.generateSourceCode(sb: StringBuilder) {
    entries.forEachIndexed { index, formParListEntry ->
        formParListEntry.type.generateSourceCode(sb)
        sb.append(" ")
        sb.append(formParListEntry.ident.name)
        if (index != entries.lastIndex) {
            sb.append(", ")
        }
    }
}

fun org.azauner.minicpp.ast.node.VoidFormParListChild.generateSourceCode(sb: StringBuilder) {
    sb.append("void")
}
