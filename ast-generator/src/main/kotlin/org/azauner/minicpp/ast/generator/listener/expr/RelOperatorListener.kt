package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class RelOperatorListener : minicppBaseListener() {

    private var relOperators = Collections.synchronizedList(mutableListOf<org.azauner.minicpp.ast.node.RelOperator>())

    override fun exitEqualEqualOperator(ctx: minicppParser.EqualEqualOperatorContext?) {
        relOperators.add(org.azauner.minicpp.ast.node.RelOperator.EQUAL)
    }

    override fun exitNotEqualOperator(ctx: minicppParser.NotEqualOperatorContext?) {
        relOperators.add(org.azauner.minicpp.ast.node.RelOperator.NOT_EQUAL)
    }

    override fun exitLessThanOperator(ctx: minicppParser.LessThanOperatorContext?) {
        relOperators.add(org.azauner.minicpp.ast.node.RelOperator.LESS_THAN)
    }

    override fun exitLessEqualOperator(ctx: minicppParser.LessEqualOperatorContext?) {
        relOperators.add(org.azauner.minicpp.ast.node.RelOperator.LESS_THAN_EQUAL)

    }

    override fun exitGreaterThanOperator(ctx: minicppParser.GreaterThanOperatorContext?) {
        relOperators.add(org.azauner.minicpp.ast.node.RelOperator.GREATER_THAN)
    }

    override fun exitGreaterEqualOperator(ctx: minicppParser.GreaterEqualOperatorContext?) {
        relOperators.add(org.azauner.minicpp.ast.node.RelOperator.GREATER_THAN_EQUAL)
    }

    fun getRelOperator(): org.azauner.minicpp.ast.node.RelOperator {
        return relOperators.removeLast()
    }
}
