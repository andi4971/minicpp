package org.azauner.minicpp.ast.generator.visitor.expr

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.parser.minicppBaseVisitor

class SignVisitor : minicppBaseVisitor<org.azauner.minicpp.ast.node.Sign>() {

    override fun visitTerminal(node: TerminalNode): org.azauner.minicpp.ast.node.Sign {
        return when (node.text) {
            "+" -> org.azauner.minicpp.ast.node.Sign.PLUS
            "-" -> org.azauner.minicpp.ast.node.Sign.MINUS
            else -> throw IllegalArgumentException("Unknown sign: ${node.text}")
        }
    }
}
