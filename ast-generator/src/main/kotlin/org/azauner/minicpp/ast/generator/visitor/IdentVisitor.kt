package org.azauner.minicpp.ast.generator.visitor

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.parser.minicppBaseVisitor

class IdentVisitor : minicppBaseVisitor<org.azauner.minicpp.ast.node.Ident>() {
    override fun visitTerminal(node: TerminalNode): org.azauner.minicpp.ast.node.Ident {
        return org.azauner.minicpp.ast.node.Ident(node.text)
    }
}
