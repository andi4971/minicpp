import org.azauner.ast.node.ConstDef
import org.azauner.ast.node.Init
import org.azauner.ast.node.NullPtrType
import org.azauner.ast.node.VarDef

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
    when(this.value) {
        NullPtrType -> sb.append("nullptr")
        else -> sb.append(this.value.toString())
    }
}
