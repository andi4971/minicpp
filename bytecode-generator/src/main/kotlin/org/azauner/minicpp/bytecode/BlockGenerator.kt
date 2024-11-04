package org.azauner.minicpp.bytecode

import org.azauner.minicpp.bytecode.field.LocalVarDefGenerator
import org.azauner.minicpp.bytecode.stat.StatGenerator
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor

class BlockGenerator(private val methodVisitor: MethodVisitor, private val className: String) {

    fun generate(block: org.azauner.minicpp.ast.node.Block, breakLabel: Label?) {
        block.entries.forEach { entry ->
            when (entry) {
                is org.azauner.minicpp.ast.node.ConstDef -> null // local const/final vars get pushed directly and are not stored
                is org.azauner.minicpp.ast.node.VarDef -> LocalVarDefGenerator(methodVisitor).generate(entry)
                is org.azauner.minicpp.ast.node.Stat -> StatGenerator(methodVisitor, className).generate(
                    entry,
                    breakLabel
                )
            }

        }

    }
}
