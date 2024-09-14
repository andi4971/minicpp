package org.azauner.minicpp.bytecode

import org.azauner.minicpp.ast.node.FormParListEntries
import org.azauner.minicpp.ast.node.FuncDef
import org.azauner.minicpp.ast.node.FuncHead
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

class FuncDefGenerator(private val classWriter: ClassWriter, private val className: String) {

    fun generate(funcDef: FuncDef) {
        val methodVisitor = classWriter.visitMethod(
            Opcodes.ACC_PUBLIC or Opcodes.ACC_STATIC,
            funcDef.funHead.ident.name,
            funcDef.funHead.getDescriptor(),
            null,
            null
        )

        methodVisitor.run {
            visitCode()
            BlockGenerator(methodVisitor, className).generate(funcDef.block)
            //TODO add only when no return statement found
            visitInsn(Opcodes.RETURN)
            visitEnd()
        }
    }


}

fun FuncHead.getDescriptor(): String {
    val descriptor = StringBuilder("(")
    if (formParList != null && formParList is FormParListEntries) {
        (formParList as FormParListEntries).entries.forEach {
            descriptor.append(it.type.descriptor)
        }
    }
    descriptor.append(")V")
    //descriptor.append(type.descriptor)
    return descriptor.toString()
}
