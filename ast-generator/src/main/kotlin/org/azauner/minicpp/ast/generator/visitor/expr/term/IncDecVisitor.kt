package org.azauner.minicpp.ast.generator.visitor.expr.term

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.parser.minicppBaseVisitor

class IncDecVisitor : minicppBaseVisitor<org.azauner.minicpp.ast.node.IncDec>() {

    override fun visitTerminal(node: TerminalNode): org.azauner.minicpp.ast.node.IncDec {
        return when(node.text) {
            "++" -> org.azauner.minicpp.ast.node.IncDec.INCREASE
            "--" -> org.azauner.minicpp.ast.node.IncDec.DECREASE
            else -> throw IllegalStateException("Invalid inc/dec operator")
        }
    }
}
