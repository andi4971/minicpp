package org.azauner.ast.generator.visitor

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.ast.node.Ident
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class IdentVisitor: minicppBaseVisitor<Ident>() {
    override fun visitTerminal(node: TerminalNode): Ident {
        return Ident(node.text)
    }
}
