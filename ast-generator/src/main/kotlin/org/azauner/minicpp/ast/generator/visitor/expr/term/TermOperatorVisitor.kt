package org.azauner.minicpp.ast.generator.visitor.expr.term
import org.azauner.minicpp.ast.node.TermOperator
import org.azauner.minicpp.ast.node.TermOperator.*
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class TermOperatorVisitor: minicppBaseVisitor<TermOperator>() {
    override fun visitStarOperator(ctx: minicppParser.StarOperatorContext): TermOperator {
        return MUL
    }

    override fun visitDivOperator(ctx: minicppParser.DivOperatorContext): TermOperator {
        return DIV
    }

    override fun visitModOperator(ctx: minicppParser.ModOperatorContext): TermOperator {
        return MOD
    }
}
