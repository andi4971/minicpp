package org.azauner.minicpp.ast.generator.visitor

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.minicpp.ast.node.Ident
import org.azauner.parser.minicppBaseVisitor

class IdentVisitor: minicppBaseVisitor<Ident>() {
    override fun visitTerminal(node: TerminalNode): Ident {
        return Ident(node.text)
    }
}
