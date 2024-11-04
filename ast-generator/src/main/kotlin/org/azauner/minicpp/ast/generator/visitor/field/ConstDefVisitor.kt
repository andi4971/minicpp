package org.azauner.minicpp.ast.generator.visitor.field
import org.azauner.minicpp.ast.generator.visitor.IdentVisitor
import org.azauner.minicpp.ast.generator.visitor.TypeVisitor
import org.azauner.minicpp.ast.util.getExprType
import org.azauner.minicpp.ast.util.requireSemantic
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class ConstDefVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.ConstDef>() {

    override fun visitConstDef(ctx: minicppParser.ConstDefContext): org.azauner.minicpp.ast.node.ConstDef {
        val type = ctx.type().accept(TypeVisitor())
        val idents = ctx.constDefEntry().map { entry ->
            val ident = entry.IDENT().accept(IdentVisitor())
            val init = entry.init().accept(InitVisitor())

            validateTypes(type, init.value.getExprType())

            val variable = scope.addVariable(ident, type, const = true, constValue = init.value.getValue())
            org.azauner.minicpp.ast.node.ConstDefEntry(ident, init, variable)
        }

        return org.azauner.minicpp.ast.node.ConstDef(type, idents)
    }

    private fun validateTypes(
        type: org.azauner.minicpp.ast.node.ExprType,
        initType: org.azauner.minicpp.ast.node.ExprType
    ) {
        requireSemantic(initType == type) {
            "Type mismatch: $initType != $type"
        }
    }
}
