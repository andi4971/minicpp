package org.azauner.ast.generator.visitor.expr

import org.azauner.ast.node.AssignOperator
import org.azauner.ast.node.AssignOperator.*
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class ExprAssignVisitor: minicppBaseVisitor<AssignOperator>() {

    override fun visitEqualAssign(ctx: minicppParser.EqualAssignContext): AssignOperator {
        return ASSIGN
    }

    override fun visitAddAssign(ctx: minicppParser.AddAssignContext): AssignOperator {
        return ADD_ASSIGN
    }

    override fun visitSubAssign(ctx: minicppParser.SubAssignContext): AssignOperator {
        return SUB_ASSIGN
    }

    override fun visitMulAssign(ctx: minicppParser.MulAssignContext): AssignOperator {
        return MUL_ASSIGN
    }

    override fun visitDivAssign(ctx: minicppParser.DivAssignContext): AssignOperator {
        return DIV_ASSIGN
    }

    override fun visitModAssign(ctx: minicppParser.ModAssignContext): AssignOperator {
        return MOD_ASSIGN
    }
}
