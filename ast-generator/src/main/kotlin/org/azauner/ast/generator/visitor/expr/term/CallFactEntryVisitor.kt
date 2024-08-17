package org.azauner.ast.generator.visitor.expr.term

import org.azauner.ast.node.ActionFact
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class CallFactEntryVisitor: minicppBaseVisitor<ActionFact>() {

    override fun visitCallFactEntry(ctx: minicppParser.CallFactEntryContext): ActionFact {
        ctx.
    }
}
