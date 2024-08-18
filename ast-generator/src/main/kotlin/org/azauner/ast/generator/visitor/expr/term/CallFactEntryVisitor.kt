package org.azauner.ast.generator.visitor.expr.term

import org.azauner.ast.generator.visitor.IdentVisitor
import org.azauner.ast.node.ActionFact
import org.azauner.ast.node.IncDec
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class CallFactEntryVisitor: minicppBaseVisitor<ActionFact>() {

    override fun visitCallFactEntry(ctx: minicppParser.CallFactEntryContext): ActionFact {
        return ActionFact(
            //Todo check if it actually works
            prefix = ctx.preIncDec?.tokenIndex?.let { ctx.INC_DEC(it)?.accept(IncDecVisitor()) },
            ident = ctx.IDENT().accept(IdentVisitor()),
            suffix = ctx.postIncDec?.tokenIndex?.let { ctx.INC_DEC(it)?.accept(IncDecVisitor()) },
            actionOp = ctx.callFactEntryOperation()?.accept(CallFactEntryOperationVisitor())
        )
    }
}
