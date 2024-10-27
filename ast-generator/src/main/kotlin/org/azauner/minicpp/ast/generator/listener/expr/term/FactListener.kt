package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.minicpp.ast.generator.listener.field.TypeListener
import org.azauner.minicpp.ast.node.*
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class FactListener(
    private val callFactEntryListener: CallFactEntryListener,
    private val typeListener: TypeListener,
    private val exprListener: ExprListener
) :
    minicppBaseListener() {

    private var facts = mutableListOf<Fact>()

    override fun exitBooleanFact(ctx: minicppParser.BooleanFactContext) {
        facts.add(BoolType(ctx.BOOLEAN().text == "true"))
    }

    override fun exitNullptrFact(ctx: minicppParser.NullptrFactContext) {
        facts.add(NullPtrType)
    }

    override fun exitIntFact(ctx: minicppParser.IntFactContext) {
        facts.add(IntType(ctx.INT().text.toInt()))
    }

    override fun exitCallFact(ctx: minicppParser.CallFactContext) {
        facts.add(callFactEntryListener.getCallFactEntry())
    }

    override fun exitNewArrayFact(ctx: minicppParser.NewArrayFactContext) {
        facts.add(
            NewArrayTypeFact(
                type = typeListener.getType(),
                expr = exprListener.getExpr()
            )
        )
    }

    override fun exitExprFact(ctx: minicppParser.ExprFactContext) {
        facts.add(ExprFact(expr = exprListener.getExpr()))
    }

    fun getFact(): Fact {
        return facts.removeLast()
    }
}
