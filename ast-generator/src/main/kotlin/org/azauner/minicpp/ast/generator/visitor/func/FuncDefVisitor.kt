package org.azauner.minicpp.ast.generator.visitor.func

import org.azauner.minicpp.ast.generator.visitor.block.BlockVisitor
import org.azauner.minicpp.ast.node.FormParListEntries
import org.azauner.minicpp.ast.node.FuncDef
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FuncDefVisitor(private val scope: Scope) : minicppBaseVisitor<FuncDef>() {
    override fun visitFuncDef(ctx: minicppParser.FuncDefContext): FuncDef {
        val funHead = ctx.funcHead().accept(FuncHeadVisitor())
        val childScope = Scope(parent = scope)
        funHead.run {
            scope.addFunction(
                ident = ident,
                returnType = type,
                formParList = formParList,
                definesFunction = true
            )

            if (formParList is FormParListEntries) {
                formParList.entries.forEach { entry ->
                    childScope.addVariable(
                        ident = entry.ident,
                        type = entry.type
                    )
                }
            }
        }

        val funcDef = FuncDef(
            funHead = funHead,
            block = ctx.block().accept(BlockVisitor(childScope))
        )
        return funcDef
    }
}
