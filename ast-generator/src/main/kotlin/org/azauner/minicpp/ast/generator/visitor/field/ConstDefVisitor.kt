package org.azauner.minicpp.ast.generator.visitor.field
import org.azauner.minicpp.ast.generator.visitor.IdentVisitor
import org.azauner.minicpp.ast.generator.visitor.TypeVisitor
import org.azauner.minicpp.ast.node.ConstDef
import org.azauner.minicpp.ast.node.ConstDefEntry
import org.azauner.minicpp.ast.node.ExprType
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.minicpp.ast.util.getExprType
import org.azauner.minicpp.ast.util.requireSemantic
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser
class ConstDefVisitor(private val scope: Scope) : minicppBaseVisitor<ConstDef>() {

    override fun visitConstDef(ctx: minicppParser.ConstDefContext): ConstDef {
        val type = ctx.type().accept(TypeVisitor())
        val idents = ctx.constDefEntry().map { entry ->
            val ident = entry.IDENT().accept(IdentVisitor())
            val init = entry.init().accept(InitVisitor())
            ConstDefEntry(ident, init)
        }
        idents.forEach { node ->
            validateNodeInit(node, type)
            scope.addVariable(node.ident, type, const = true)
        }
        return ConstDef(type, idents)
    }

    private fun validateNodeInit(node: ConstDefEntry, type: ExprType) {
        val initType = node.value.value.getExprType()
        requireSemantic(initType == type) {
            "Type mismatch: $initType != $type"
        }
    }
}
