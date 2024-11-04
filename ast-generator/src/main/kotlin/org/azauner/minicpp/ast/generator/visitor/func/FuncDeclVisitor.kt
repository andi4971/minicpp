package org.azauner.minicpp.ast.generator.visitor.func
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class FuncDeclVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.FuncDecl>() {
    override fun visitFuncDecl(ctx: minicppParser.FuncDeclContext): org.azauner.minicpp.ast.node.FuncDecl {
        val funcDecl = org.azauner.minicpp.ast.node.FuncDecl(ctx.funcHead().accept(FuncHeadVisitor()))

        funcDecl.funcHead.run {
            scope.addFunction(
                ident = ident,
                returnType = type,
                formParList = formParList ?: org.azauner.minicpp.ast.node.VoidFormParListChild,
                definesFunction = false
            )
        }

        return funcDecl
    }
}
