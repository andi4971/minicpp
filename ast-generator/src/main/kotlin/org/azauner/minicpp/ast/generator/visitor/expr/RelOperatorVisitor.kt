package org.azauner.minicpp.ast.generator.visitor.expr

import org.azauner.minicpp.ast.node.RelOperator.*
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class RelOperatorVisitor : minicppBaseVisitor<org.azauner.minicpp.ast.node.RelOperator>() {

    override fun visitEqualEqualOperator(ctx: minicppParser.EqualEqualOperatorContext?): org.azauner.minicpp.ast.node.RelOperator {
        return EQUAL
    }

    override fun visitNotEqualOperator(ctx: minicppParser.NotEqualOperatorContext?): org.azauner.minicpp.ast.node.RelOperator {
        return NOT_EQUAL
    }

    override fun visitLessThanOperator(ctx: minicppParser.LessThanOperatorContext?): org.azauner.minicpp.ast.node.RelOperator {
        return LESS_THAN
    }

    override fun visitLessEqualOperator(ctx: minicppParser.LessEqualOperatorContext?): org.azauner.minicpp.ast.node.RelOperator {
        return LESS_THAN_EQUAL
    }

    override fun visitGreaterThanOperator(ctx: minicppParser.GreaterThanOperatorContext?): org.azauner.minicpp.ast.node.RelOperator {
        return GREATER_THAN
    }

    override fun visitGreaterEqualOperator(ctx: minicppParser.GreaterEqualOperatorContext?): org.azauner.minicpp.ast.node.RelOperator {
        return GREATER_THAN_EQUAL
    }
}
