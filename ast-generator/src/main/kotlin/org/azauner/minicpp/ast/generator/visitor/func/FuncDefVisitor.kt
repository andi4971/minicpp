package org.azauner.minicpp.ast.generator.visitor.func

import org.azauner.minicpp.ast.generator.visitor.block.BlockVisitor
import org.azauner.minicpp.ast.node.FormParListEntries
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FuncDefVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.FuncDef>() {
    override fun visitFuncDef(ctx: minicppParser.FuncDefContext): org.azauner.minicpp.ast.node.FuncDef {
        val funHead = ctx.funcHead().accept(FuncHeadVisitor())
        val childScope = org.azauner.minicpp.ast.node.scope.Scope(parent = scope)
        funHead.run {
            scope.addFunction(
                ident = ident,
                returnType = type,
                formParList = formParList,
                definesFunction = true
            )

            (formParList as? FormParListEntries)?.entries?.forEach { entry ->
                childScope.addVariable(
                    ident = entry.ident,
                    type = entry.type
                )
            }
        }

        val funcDef = org.azauner.minicpp.ast.node.FuncDef(
            funHead = funHead,
            block = ctx.block().accept(BlockVisitor(childScope))
        )
        return funcDef
    }
}
