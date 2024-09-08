import org.azauner.ast.node.Block
import org.azauner.ast.node.ConstDef
import org.azauner.ast.node.Stat
import org.azauner.ast.node.VarDef

fun Block.generateSourceCode(sb: StringBuilder) {
    sb.appendLine("{")
    entries.forEach {
       when(it) {
           is ConstDef -> it.generateSourceCode(sb)
           is Stat -> it.generateSourceCode(sb)
           is VarDef -> it.generateSourceCode(sb)
       }
    }
    sb.appendLine("}")
}
