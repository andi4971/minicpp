package org.azauner.minicpp.ast.generator.visitor.expr.term
import org.azauner.minicpp.ast.generator.visitor.TypeVisitor
import org.azauner.minicpp.ast.generator.visitor.expr.ExprVisitor
import org.azauner.minicpp.ast.generator.visitor.field.BooleanVisitor
import org.azauner.minicpp.ast.generator.visitor.field.IntVisitor
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FactVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.Fact>() {

    override fun visitBooleanFact(ctx: minicppParser.BooleanFactContext): org.azauner.minicpp.ast.node.Fact {
        return ctx.BOOLEAN().accept(BooleanVisitor())
    }

    override fun visitNullptrFact(ctx: minicppParser.NullptrFactContext): org.azauner.minicpp.ast.node.Fact {
        return org.azauner.minicpp.ast.node.NullPtrType
    }

    override fun visitIntFact(ctx: minicppParser.IntFactContext): org.azauner.minicpp.ast.node.Fact {
        return ctx.INT().accept(IntVisitor())
    }

    override fun visitCallFact(ctx: minicppParser.CallFactContext): org.azauner.minicpp.ast.node.Fact {
        return ctx.callFactEntry().accept(CallFactEntryVisitor(scope))
    }


    override fun visitNewArrayFact(ctx: minicppParser.NewArrayFactContext): org.azauner.minicpp.ast.node.Fact {
        return org.azauner.minicpp.ast.node.NewArrayTypeFact(
            type = ctx.type().accept(TypeVisitor()),
            expr = ctx.expr().accept(ExprVisitor(scope))
        )
    }

    override fun visitExprFact(ctx: minicppParser.ExprFactContext): org.azauner.minicpp.ast.node.Fact {
        return org.azauner.minicpp.ast.node.ExprFact(ctx.expr().accept(ExprVisitor(scope)))
    }
}
