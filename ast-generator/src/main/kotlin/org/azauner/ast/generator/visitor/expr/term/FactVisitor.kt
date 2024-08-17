package org.azauner.ast.generator.visitor.expr.term
import org.azauner.ast.node.Fact
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser
class FactVisitor: minicppBaseVisitor<Fact>() {

    override fun visitFact(ctx: minicppParser.FactContext): Fact {
        return Fact(

        )
    }
}
