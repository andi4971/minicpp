package org.azauner.ast.generator.visitor.expr

import org.azauner.ast.node.RelOperator
import org.azauner.ast.node.RelOperator.*
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class RelOperatorVisitor: minicppBaseVisitor<RelOperator>() {

    override fun visitEqualEqualOperator(ctx: minicppParser.EqualEqualOperatorContext?): RelOperator {
        return EQUAL
    }

    override fun visitNotEqualOperator(ctx: minicppParser.NotEqualOperatorContext?): RelOperator {
        return NOT_EQUAL
    }

    override fun visitLessThanOperator(ctx: minicppParser.LessThanOperatorContext?): RelOperator {
        return LESS_THAN
    }

    override fun visitLessEqualOperator(ctx: minicppParser.LessEqualOperatorContext?): RelOperator {
        return LESS_THAN_EQUAL
    }

    override fun visitGreaterThanOperator(ctx: minicppParser.GreaterThanOperatorContext?): RelOperator {
        return GREATER_THAN
    }

    override fun visitGreaterEqualOperator(ctx: minicppParser.GreaterEqualOperatorContext?): RelOperator {
        return GREATER_THAN_EQUAL
    }
}
