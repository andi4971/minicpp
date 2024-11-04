package org.azauner.minicpp.bytecode.field

import org.objectweb.asm.ClassWriter

class StaticConstDefGenerator(private val cw: ClassWriter) {

    fun generateStatic(constDef: org.azauner.minicpp.ast.node.ConstDef) {
        constDef.entries.forEach { entry ->
            val index = cw.newConst(entry.value.value.getValue())
            entry.variable.index = index
        }
    }
}

