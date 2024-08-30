package org.azauner.ast.generator.visitor.func
import org.azauner.ast.node.FuncDecl
import org.azauner.ast.node.VoidFormParListChild
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FuncDeclVisitor(private val scope: Scope) : minicppBaseVisitor<FuncDecl>() {
    override fun visitFuncDecl(ctx: minicppParser.FuncDeclContext): FuncDecl {
        val funcDecl =  FuncDecl(ctx.funcHead().accept(FuncHeadVisitor()))

        funcDecl.funcHead.run {
            scope.addFunction(
                ident = ident,
                returnType = type,
                formParList= formParList ?: VoidFormParListChild
            )
        }

        return funcDecl
    }
}
