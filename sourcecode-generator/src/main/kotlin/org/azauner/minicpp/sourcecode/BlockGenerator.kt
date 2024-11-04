package org.azauner.minicpp.sourcecode

fun org.azauner.minicpp.ast.node.Block.generateSourceCode(sb: StringBuilder) {
    sb.appendLine("{")
    entries.forEach {
       when(it) {
           is org.azauner.minicpp.ast.node.ConstDef -> it.generateSourceCode(sb)
           is org.azauner.minicpp.ast.node.Stat -> it.generateSourceCode(sb)
           is org.azauner.minicpp.ast.node.VarDef -> it.generateSourceCode(sb)
       }
    }
    sb.appendLine("}")
}
