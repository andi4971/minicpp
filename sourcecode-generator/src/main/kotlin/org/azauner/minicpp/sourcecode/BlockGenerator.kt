package org.azauner.minicpp.sourcecode

import org.azauner.minicpp.ast.node.Block
import org.azauner.minicpp.ast.node.ConstDef
import org.azauner.minicpp.ast.node.Stat
import org.azauner.minicpp.ast.node.VarDef

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
