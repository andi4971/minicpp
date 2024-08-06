package org.azauner.ast.generator

import org.antlr.v4.runtime.tree.TerminalNode
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser

class MiniCppListener(val parser: minicppParser): minicppBaseListener() {


    override fun enterInit(ctx: minicppParser.InitContext) {
        println(ctx)
    }

    override fun visitTerminal(node: TerminalNode) {
        println(node)
    }

    override fun exitInit(ctx: minicppParser.InitContext) {
        println(ctx)
    }

    override fun enterConstDef(ctx: minicppParser.ConstDefContext) {
        println(ctx)
    }

    override fun enterVarDef(ctx: minicppParser.VarDefContext?) {
        println(ctx)
    }
}
