package org.azauner.minicpp.bytecode

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ACONST_NULL

fun MethodVisitor.pushBoolValue(value: Boolean) {
    this.visitLdcInsn(value)
}
fun MethodVisitor.pushIntValue(value: Int) {
    this.visitLdcInsn(value)
}
fun MethodVisitor.pushNullValue() {
    this.visitInsn(ACONST_NULL)
}
