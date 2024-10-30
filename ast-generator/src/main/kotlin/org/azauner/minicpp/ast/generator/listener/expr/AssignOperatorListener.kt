package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.minicpp.ast.node.AssignOperator
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class AssignOperatorListener: minicppBaseListener() {

    private var assignOperators = Collections.synchronizedList(mutableListOf<AssignOperator>())

    override fun exitEqualAssign(ctx: minicppParser.EqualAssignContext?) {
        assignOperators.add(AssignOperator.ASSIGN)
    }

    override fun exitAddAssign(ctx: minicppParser.AddAssignContext?) {
        assignOperators.add(AssignOperator.ADD_ASSIGN)
    }

    override fun exitSubAssign(ctx: minicppParser.SubAssignContext?) {
        assignOperators.add(AssignOperator.SUB_ASSIGN)
    }

    override fun exitMulAssign(ctx: minicppParser.MulAssignContext?) {
        assignOperators.add(AssignOperator.MUL_ASSIGN)
    }

    override fun exitDivAssign(ctx: minicppParser.DivAssignContext?) {
        assignOperators.add(AssignOperator.DIV_ASSIGN)
    }

    override fun exitModAssign(ctx: minicppParser.ModAssignContext?) {
        assignOperators.add(AssignOperator.MOD_ASSIGN)
    }

    fun getAssignOperator(): AssignOperator {
        return assignOperators.removeLast()
    }
}
