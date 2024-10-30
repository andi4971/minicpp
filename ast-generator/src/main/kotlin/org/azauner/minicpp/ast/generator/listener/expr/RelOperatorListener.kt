package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.node.RelOperator
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class RelOperatorListener : minicppBaseListener() {

    private var relOperators = Collections.synchronizedList(mutableListOf<RelOperator>())

    override fun exitEqualEqualOperator(ctx: minicppParser.EqualEqualOperatorContext?) {
        relOperators.add(RelOperator.EQUAL)
    }

    override fun exitNotEqualOperator(ctx: minicppParser.NotEqualOperatorContext?) {
        relOperators.add(RelOperator.NOT_EQUAL)
    }

    override fun exitLessThanOperator(ctx: minicppParser.LessThanOperatorContext?) {
        relOperators.add(RelOperator.LESS_THAN)
    }

    override fun exitLessEqualOperator(ctx: minicppParser.LessEqualOperatorContext?) {
        relOperators.add(RelOperator.LESS_THAN_EQUAL)

    }

    override fun exitGreaterThanOperator(ctx: minicppParser.GreaterThanOperatorContext?) {
        relOperators.add(RelOperator.GREATER_THAN)
    }

    override fun exitGreaterEqualOperator(ctx: minicppParser.GreaterEqualOperatorContext?) {
        relOperators.add(RelOperator.GREATER_THAN_EQUAL)
    }

    fun getRelOperator(): RelOperator {
        return relOperators.removeLast()
    }
}
