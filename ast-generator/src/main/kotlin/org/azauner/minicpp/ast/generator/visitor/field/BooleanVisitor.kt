package org.azauner.minicpp.ast.generator.visitor.field

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.parser.minicppBaseVisitor

class BooleanVisitor : minicppBaseVisitor<org.azauner.minicpp.ast.node.BoolType>() {
    override fun visitTerminal(node: TerminalNode): org.azauner.minicpp.ast.node.BoolType {
        return when (node.text) {
            "true" -> org.azauner.minicpp.ast.node.BoolType(true)
            "false" -> org.azauner.minicpp.ast.node.BoolType(false)
            else -> throw IllegalStateException("Unknown boolean init")
        }
    }
}
