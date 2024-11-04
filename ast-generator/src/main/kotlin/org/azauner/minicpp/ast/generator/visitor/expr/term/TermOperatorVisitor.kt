package org.azauner.minicpp.ast.generator.visitor.expr.term
import org.azauner.minicpp.ast.node.TermOperator.*
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class TermOperatorVisitor : minicppBaseVisitor<org.azauner.minicpp.ast.node.TermOperator>() {
    override fun visitStarOperator(ctx: minicppParser.StarOperatorContext): org.azauner.minicpp.ast.node.TermOperator {
        return MUL
    }

    override fun visitDivOperator(ctx: minicppParser.DivOperatorContext): org.azauner.minicpp.ast.node.TermOperator {
        return DIV
    }

    override fun visitModOperator(ctx: minicppParser.ModOperatorContext): org.azauner.minicpp.ast.node.TermOperator {
        return MOD
    }
}
