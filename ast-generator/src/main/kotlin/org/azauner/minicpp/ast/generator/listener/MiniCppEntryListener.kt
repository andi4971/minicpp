package org.azauner.minicpp.ast.generator.listener

import org.azauner.minicpp.ast.generator.listener.field.ConstDefListener
import org.azauner.minicpp.ast.generator.listener.field.VarDefListener
import org.azauner.minicpp.ast.generator.listener.func.FuncDeclListener
import org.azauner.minicpp.ast.generator.listener.func.FuncDefListener
import org.azauner.minicpp.ast.node.MiniCppEntry
import org.azauner.parser.minicppBaseListener
import org.azauner.parser.minicppParser
import java.util.*

class MiniCppEntryListener(private val constDefListener: ConstDefListener, private val varDefListener: VarDefListener,
    private val funcDeclListener: FuncDeclListener,
    private val funcDefListener: FuncDefListener) :
    minicppBaseListener() {

    private val entries = Collections.synchronizedList(mutableListOf<MiniCppEntry>())

    override fun exitMiniCppEntry(ctx: minicppParser.MiniCppEntryContext) {
        val entry = when {
            ctx.constDef() != null -> constDefListener.getConstDef()
            ctx.varDef() != null -> varDefListener.getVarDef()
            ctx.funcDecl() != null -> funcDeclListener.getFuncDecl()
            ctx.funcDef() != null -> funcDefListener.getFuncDef()
            else -> null
        }
        entry?.let { entries.add(it) }
    }

    fun getEntry(): MiniCppEntry {
        return entries.removeLast()
    }

    fun getAllEntries(): List<MiniCppEntry> {
        return entries
    }
}
