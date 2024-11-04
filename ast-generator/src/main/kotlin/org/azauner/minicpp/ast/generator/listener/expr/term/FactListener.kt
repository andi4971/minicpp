package org.azauner.minicpp.ast.generator.listener.expr.term

import org.azauner.minicpp.ast.generator.listener.expr.ExprListener
import org.azauner.minicpp.ast.generator.listener.field.TypeListener
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class FactListener(
    private val callFactEntryListener: CallFactEntryListener,
    private val typeListener: TypeListener,
    private val exprListener: ExprListener
) :
    minicppBaseListener() {

    private var facts = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.Fact>())

    override fun exitBooleanFact(ctx: minicppParser.BooleanFactContext) {
        facts.add(org.azauner.minicpp.ast.node.BoolType(ctx.BOOLEAN().text == "true"))
    }

    override fun exitNullptrFact(ctx: minicppParser.NullptrFactContext) {
        facts.add(org.azauner.minicpp.ast.node.NullPtrType)
    }

    override fun exitIntFact(ctx: minicppParser.IntFactContext) {
        facts.add(org.azauner.minicpp.ast.node.IntType(ctx.INT().text.toInt()))
    }

    override fun exitCallFact(ctx: minicppParser.CallFactContext) {
        facts.add(callFactEntryListener.getCallFactEntry())
    }

    override fun exitNewArrayFact(ctx: minicppParser.NewArrayFactContext) {
        facts.add(
            org.azauner.minicpp.ast.node.NewArrayTypeFact(
                type = typeListener.getType(),
                expr = exprListener.getExpr()
            )
        )
    }

    override fun exitExprFact(ctx: minicppParser.ExprFactContext) {
        facts.add(org.azauner.minicpp.ast.node.ExprFact(expr = exprListener.getExpr()))
    }

    fun getFact(): org.azauner.minicpp.ast.node.Fact {
        return facts.removeLast()
    }
}
