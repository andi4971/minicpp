package org.azauner.ast.generator

import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class MIniCppVisitor: minicppBaseVisitor<Any>() {
    override fun visitInit(ctx: minicppParser.InitContext): Any {
        println(ctx.text)
        return ""
    }

    override fun visitFuncDef(ctx: minicppParser.FuncDefContext): Any {
        println(ctx.text)
        return ""
    }
}
