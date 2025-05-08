package org.azauner.minicpp.bytecode.field

import org.azauner.minicpp.ast.node.MiniCpp
import org.azauner.minicpp.ast.util.toPointerTypeOptional
import org.azauner.minicpp.bytecode.MiniCppGenerator.Companion.scannerVarName
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class StaticVarDefGenerator(private val cw: ClassWriter) {

    private val generatedVarDefs = mutableListOf<org.azauner.minicpp.ast.node.VarDef>()

    fun generateStatic(varDef: org.azauner.minicpp.ast.node.VarDef) {
        varDef.entries.forEach { entry ->
            cw.visitField(
                ACC_PUBLIC
                        or ACC_STATIC,
                entry.ident.name,
                varDef.type.toPointerTypeOptional(entry.pointer).descriptor,
                null,
                null//entry.value?.value?.getValue()
            )
        }
        generatedVarDefs.add(varDef)
    }

    fun generateStaticInitBlock(miniCpp: MiniCpp) {
        cw.visitMethod(
            ACC_STATIC,
            "<clinit>",
            "()V",
            null,
            null
        ).apply {
            visitCode()
            generatedVarDefs.forEach { varDef ->
                varDef.entries
                    .filter{ it.value != null }
                    .forEach { entry ->
                    visitLdcInsn(entry.value?.value?.getValue())
                    visitFieldInsn(
                        PUTSTATIC,
                        miniCpp.className,
                        entry.ident.name,
                        varDef.type.toPointerTypeOptional(entry.pointer).descriptor
                    )
                }
            }
            visitScannerInit(miniCpp)
            visitInsn(RETURN)
            visitMaxs(0, 0)
            visitEnd()
        }
    }
}

private fun MethodVisitor.visitScannerInit(miniCpp: org.azauner.minicpp.ast.node.MiniCpp) {
    ensureScannerNameNotTaken(miniCpp)

    visitTypeInsn(NEW, SCANNER_QUAL_NAME)
    visitInsn(DUP)
    visitFieldInsn(GETSTATIC, "java/lang/System", "in", INPUT_STREAM_DESC)
    visitMethodInsn(INVOKESPECIAL, SCANNER_QUAL_NAME, "<init>", SCANNER_INIT_DESC, false)
    visitFieldInsn(PUTSTATIC, miniCpp.className, scannerVarName, SCANNER_DESC)
}

fun ensureScannerNameNotTaken(miniCpp: org.azauner.minicpp.ast.node.MiniCpp) {
    while (miniCpp.globalScope.variableExistsInSelfOrChildren(org.azauner.minicpp.ast.node.Ident(scannerVarName))) {
        scannerVarName = "_$scannerVarName"
    }
}
