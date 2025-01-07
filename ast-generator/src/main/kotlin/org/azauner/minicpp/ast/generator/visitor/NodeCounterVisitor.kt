package org.azauner.minicpp.ast.generator.visitor

import org.antlr.v4.runtime.tree.*

class NodeCounterVisitor : ParseTreeVisitor<Int> {
    override fun visit(tree: ParseTree): Int {
        var count = 1 // Count the current node
        for (i in 0 until tree.childCount) {
            count += visit(tree.getChild(i))
        }
        return count
    }

    override fun visitChildren(p0: RuleNode): Int {
        return visit(p0)
    }

    override fun visitTerminal(node: TerminalNode): Int {
        return 1
    }

    override fun visitErrorNode(p0: ErrorNode): Int {
        return 1
    }

}
