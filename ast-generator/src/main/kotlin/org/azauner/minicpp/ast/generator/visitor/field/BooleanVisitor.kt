package org.azauner.minicpp.ast.generator.visitor.field

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.minicpp.ast.node.BoolType
import org.azauner.parser.minicppBaseVisitor

class BooleanVisitor: minicppBaseVisitor<BoolType>() {
    override fun visitTerminal(node: TerminalNode): BoolType {
        return when (node.text) {
            "true" -> BoolType(true)
            "false" -> BoolType(false)
            else -> throw IllegalStateException("Unknown boolean init")
        }
    }
}
