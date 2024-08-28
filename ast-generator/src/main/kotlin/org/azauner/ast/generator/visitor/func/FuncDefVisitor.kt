package org.azauner.ast.generator.visitor.func
import org.azauner.ast.generator.visitor.block.BlockVisitor
import org.azauner.ast.node.FormParListEntries
import org.azauner.ast.node.FuncDef
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FuncDefVisitor(private val scope: Scope) : minicppBaseVisitor<FuncDef>() {
    override fun visitFuncDef(ctx: minicppParser.FuncDefContext): FuncDef {
        val funHead = ctx.funcHead().accept(FuncHeadVisitor())
        val childScope = Scope(parent = scope)
        funHead.run {
            if(!scope.functionExists(this)) {
                scope.addFunction(
                    ident = ident,
                    returnType = type,
                    returnTypePointer = pointer,
                    formParList = formParList
                )
            }
            if(formParList is FormParListEntries) {
                formParList.entries.forEach { entry ->
                    childScope.addVariable(
                        ident = entry.ident,
                        type = entry.type,
                        pointer = entry.pointer
                    )
                }
            }
        }

        val funcDef =  FuncDef(
            funHead = funHead,
            block = ctx.block().accept(BlockVisitor(childScope))
        )
        return funcDef
    }
}
