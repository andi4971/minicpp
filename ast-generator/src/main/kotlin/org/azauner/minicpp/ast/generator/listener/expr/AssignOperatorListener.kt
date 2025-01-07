package org.azauner.minicpp.ast.generator.listener.expr

import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class AssignOperatorListener: minicppBaseListener() {

    private var assignOperators =
        mutableListOf<org.azauner.minicpp.ast.node.AssignOperator>()

    override fun exitEqualAssign(ctx: minicppParser.EqualAssignContext?) {
        assignOperators.add(org.azauner.minicpp.ast.node.AssignOperator.ASSIGN)
    }

    override fun exitAddAssign(ctx: minicppParser.AddAssignContext?) {
        assignOperators.add(org.azauner.minicpp.ast.node.AssignOperator.ADD_ASSIGN)
    }

    override fun exitSubAssign(ctx: minicppParser.SubAssignContext?) {
        assignOperators.add(org.azauner.minicpp.ast.node.AssignOperator.SUB_ASSIGN)
    }

    override fun exitMulAssign(ctx: minicppParser.MulAssignContext?) {
        assignOperators.add(org.azauner.minicpp.ast.node.AssignOperator.MUL_ASSIGN)
    }

    override fun exitDivAssign(ctx: minicppParser.DivAssignContext?) {
        assignOperators.add(org.azauner.minicpp.ast.node.AssignOperator.DIV_ASSIGN)
    }

    override fun exitModAssign(ctx: minicppParser.ModAssignContext?) {
        assignOperators.add(org.azauner.minicpp.ast.node.AssignOperator.MOD_ASSIGN)
    }

    fun getAssignOperator(): org.azauner.minicpp.ast.node.AssignOperator {
        return assignOperators.removeLast()
    }
}
