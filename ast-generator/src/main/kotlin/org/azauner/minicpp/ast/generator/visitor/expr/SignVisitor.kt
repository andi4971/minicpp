package org.azauner.minicpp.ast.generator.visitor.expr

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.minicpp.ast.node.Sign
import org.azauner.parser.minicppBaseVisitor

class SignVisitor: minicppBaseVisitor<Sign>() {

    override fun visitTerminal(node: TerminalNode): Sign {
        return when (node.text) {
            "+" -> Sign.PLUS
            "-" -> Sign.MINUS
            else -> throw IllegalArgumentException("Unknown sign: ${node.text}")
        }
    }
}
