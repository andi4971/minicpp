package org.azauner.minicpp.ast.generator.visitor.field

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.minicpp.ast.node.IntType
import org.azauner.parser.minicppBaseVisitor

class IntVisitor(private val isPos: Boolean = true): minicppBaseVisitor<IntType>() {
    override fun visitTerminal(node: TerminalNode): IntType {
        val value = node.text.toIntOrNull() ?: throw IllegalStateException("Invalid int value")
        val signedValue = if (isPos) value else -value
        return IntType(signedValue)
    }
}
