package org.azauner.ast.generator.visitor.expr.term
import org.azauner.ast.generator.visitor.TypeVisitor
import org.azauner.ast.generator.visitor.expr.ExprVisitor
import org.azauner.ast.generator.visitor.field.BooleanVisitor
import org.azauner.ast.generator.visitor.field.IntVisitor
import org.azauner.ast.node.ExprFact
import org.azauner.ast.node.Fact
import org.azauner.ast.node.NewArrayTypeFact
import org.azauner.ast.node.NullPtrType
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FactVisitor: minicppBaseVisitor<Fact>() {

    override fun visitBooleanFact(ctx: minicppParser.BooleanFactContext): Fact {
        return ctx.BOOLEAN().accept(BooleanVisitor())
    }

    override fun visitNullptrFact(ctx: minicppParser.NullptrFactContext): Fact {
        return NullPtrType
    }

    override fun visitIntFact(ctx: minicppParser.IntFactContext): Fact {
        return ctx.INT().accept(IntVisitor())
    }

    override fun visitCallFact(ctx: minicppParser.CallFactContext): Fact {
        return ctx.callFactEntry().accept(CallFactEntryVisitor())
    }


    override fun visitNewArrayFact(ctx: minicppParser.NewArrayFactContext): Fact {
        return NewArrayTypeFact(
            type = ctx.type().accept(TypeVisitor()),
            expr = ctx.expr().accept(ExprVisitor())
        )
    }

    override fun visitExprFact(ctx: minicppParser.ExprFactContext): Fact {
        return ExprFact(ctx.expr().accept(ExprVisitor()))
    }
}
