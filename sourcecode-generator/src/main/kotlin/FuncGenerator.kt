import org.azauner.ast.node.*

fun FuncDecl.generateSourceCode(sb: StringBuilder) {
    funcHead.generateSourceCode(sb)
    sb.appendLine(";")
    sb.appendLine()
}

fun FuncDef.generateSourceCode(sb: StringBuilder) {
    funHead.generateSourceCode(sb)
    sb.appendLine()
    block.generateSourceCode(sb)
}

fun FuncHead.generateSourceCode(sb: StringBuilder) {
    type.generateSourceCode(sb)
    sb.append(" ")
    sb.append(ident.name)
    sb.append("(")
    formParList?.let {
        when (it) {
            is FormParListEntries -> it.generateSourceCode(sb)
            is VoidFormParListChild -> it.generateSourceCode(sb)
        }
    }
    sb.append(")")
}


fun FormParListEntries.generateSourceCode(sb: StringBuilder) {
    entries.forEachIndexed { index, formParListEntry ->
        formParListEntry.type.generateSourceCode(sb)
        sb.append(" ")
        sb.append(formParListEntry.ident.name)
        if (index != entries.lastIndex) {
            sb.append(", ")
        }
    }
}

fun VoidFormParListChild.generateSourceCode(sb: StringBuilder) {
    sb.append("void")
}
