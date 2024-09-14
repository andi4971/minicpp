package org.azauner.minicpp.bytecode

import org.azauner.minicpp.ast.node.Block
import org.azauner.minicpp.ast.node.ConstDef
import org.azauner.minicpp.ast.node.Stat
import org.azauner.minicpp.ast.node.VarDef
import org.azauner.minicpp.bytecode.field.LocalVarDefGenerator
import org.azauner.minicpp.bytecode.stat.StatGenerator
import org.objectweb.asm.MethodVisitor

class BlockGenerator(private val methodVisitor: MethodVisitor, private val className: String) {

    fun generate(block: Block) {
        block.entries.forEach { entry ->
            when (entry) {
                is ConstDef -> null // local const/final vars get pushed directly and are not stored
                is VarDef -> LocalVarDefGenerator(methodVisitor).generate(entry)
                is Stat -> StatGenerator(methodVisitor, className).generate(entry)
            }

        }

    }
}
