package org.azauner.minicpp.ast.generator.visitor.expr.term

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.minicpp.ast.node.IncDec
import org.azauner.parser.minicppBaseVisitor

class IncDecVisitor: minicppBaseVisitor<IncDec>() {

    override fun visitTerminal(node: TerminalNode): IncDec {
        return when(node.text) {
            "++" -> IncDec.INCREASE
            "--" -> IncDec.DECREASE
            else -> throw IllegalStateException("Invalid inc/dec operator")
        }
    }
}
