package org.azauner.minicpp.bytecode.field

import org.azauner.minicpp.ast.node.Ident
import org.azauner.minicpp.ast.node.MiniCpp
import org.azauner.minicpp.ast.node.VarDef
import org.azauner.minicpp.ast.util.toPointerTypeOptional
import org.azauner.minicpp.bytecode.MiniCppGenerator.Companion.scannerVarName
import org.azauner.parser.minicppLexer.NEW
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class StaticVarDefGenerator(private val cw: ClassWriter) {

    private val generatedVarDefs = mutableListOf<VarDef>()

    fun generateStatic(varDef: VarDef) {
        varDef.entries.forEach { entry ->
            cw.visitField(
                ACC_PUBLIC
                        or ACC_STATIC,
                entry.ident.name,
                varDef.type.toPointerTypeOptional(entry.pointer).descriptor,
                null,
                entry.value?.value?.getValue()
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

private fun MethodVisitor.visitScannerInit(miniCpp: MiniCpp) {
    ensureScannerNameNotTaken(miniCpp)

    visitTypeInsn(NEW, SCANNER_QUAL_NAME)
    visitInsn(DUP)
    visitFieldInsn(GETSTATIC, miniCpp.className, scannerVarName, INPUT_STREAM_DESC)
    visitMethodInsn(INVOKESPECIAL, SCANNER_QUAL_NAME, "<init>", SCANNER_INIT_DESC, false)
    visitFieldInsn(PUTSTATIC, miniCpp.className, scannerVarName, SCANNER_DESC)
}

fun ensureScannerNameNotTaken(miniCpp: MiniCpp) {
    while(miniCpp.globalScope.variableExistsInSelfOrChildren(Ident(scannerVarName))) {
        scannerVarName = "_$scannerVarName"
    }
}
