package org.azauner.minicpp.bytecode

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.RETURN

class FuncDefGenerator(private val classWriter: ClassWriter, private val className: String) {

    fun generate(funcDef: org.azauner.minicpp.ast.node.FuncDef) {
        val methodVisitor = classWriter.visitMethod(
            Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
            funcDef.funHead.ident.name,
            funcDef.funHead.getDescriptor(),
            null,
            null
        )

        methodVisitor.run {
            visitCode()
            BlockGenerator(methodVisitor, className).generate(funcDef.block, null)
            visitInsn(RETURN)
            visitMaxs(0, 0)
            visitEnd()
        }
    }


}

fun org.azauner.minicpp.ast.node.FuncHead.getDescriptor(): String {
    val descriptor = StringBuilder("(")
    if (formParList != null && formParList is org.azauner.minicpp.ast.node.FormParListEntries) {
        (formParList as org.azauner.minicpp.ast.node.FormParListEntries).entries.forEach {
            descriptor.append(it.type.descriptor)
        }
    }
    descriptor.append(")")
    descriptor.append(type.descriptor)
    return descriptor.toString()
}
