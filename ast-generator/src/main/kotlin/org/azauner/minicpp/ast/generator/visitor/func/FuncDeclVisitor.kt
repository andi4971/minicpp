package org.azauner.minicpp.ast.generator.visitor.func
import org.azauner.minicpp.ast.node.FuncDecl
import org.azauner.minicpp.ast.node.VoidFormParListChild
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FuncDeclVisitor(private val scope: Scope) : minicppBaseVisitor<FuncDecl>() {
    override fun visitFuncDecl(ctx: minicppParser.FuncDeclContext): FuncDecl {
        val funcDecl =  FuncDecl(ctx.funcHead().accept(FuncHeadVisitor()))

        funcDecl.funcHead.run {
            scope.addFunction(
                ident = ident,
                returnType = type,
                formParList= formParList ?: VoidFormParListChild,
                definesFunction = false
            )
        }

        return funcDecl
    }
}
