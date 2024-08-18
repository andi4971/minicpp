package org.azauner.ast.generator.visitor.expr.term

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.ast.node.IncDec
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class IncDecVisitor: minicppBaseVisitor<IncDec>() {

    override fun visitTerminal(node: TerminalNode): IncDec {
        return when(node.text) {
            "++" -> IncDec.INCREASE
            "--" -> IncDec.DECREASE
            else -> throw IllegalStateException("Invalid inc/dec operator")
        }
    }
}
