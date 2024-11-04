package org.azauner.minicpp.ast.generator.visitor.expr

import org.azauner.minicpp.ast.node.AssignOperator.*
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class ExprAssignVisitor : minicppBaseVisitor<org.azauner.minicpp.ast.node.AssignOperator>() {

    override fun visitEqualAssign(ctx: minicppParser.EqualAssignContext): org.azauner.minicpp.ast.node.AssignOperator {
        return ASSIGN
    }

    override fun visitAddAssign(ctx: minicppParser.AddAssignContext): org.azauner.minicpp.ast.node.AssignOperator {
        return ADD_ASSIGN
    }

    override fun visitSubAssign(ctx: minicppParser.SubAssignContext): org.azauner.minicpp.ast.node.AssignOperator {
        return SUB_ASSIGN
    }

    override fun visitMulAssign(ctx: minicppParser.MulAssignContext): org.azauner.minicpp.ast.node.AssignOperator {
        return MUL_ASSIGN
    }

    override fun visitDivAssign(ctx: minicppParser.DivAssignContext): org.azauner.minicpp.ast.node.AssignOperator {
        return DIV_ASSIGN
    }

    override fun visitModAssign(ctx: minicppParser.ModAssignContext): org.azauner.minicpp.ast.node.AssignOperator {
        return MOD_ASSIGN
    }
}
