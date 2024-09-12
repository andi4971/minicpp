package org.azauner.minicpp.bytecode.field

import org.azauner.minicpp.ast.node.ConstDef
import org.objectweb.asm.ClassWriter

class StaticConstDefGenerator(private val cw: ClassWriter) {

    fun generateStatic(constDef: ConstDef) {
        constDef.entries.forEach { entry ->
            val index = cw.newConst(entry.value.value.getValue())
            entry.variable.index = index
        }
    }

    /*   fun getFieldNode(isStatic: Boolean): List<FieldNode> {
           return constDef.entries.map { entry ->
               FieldNode(
                   Opcodes.ACC_PUBLIC
                           or( if(isStatic) Opcodes.ACC_STATIC else 0)
                           or Opcodes.ACC_FINAL,
                   entry.ident.name,
                   constDef.type.descriptor,
                   null,
                   entry.value.value.getValue()
               )
           }*/
}

