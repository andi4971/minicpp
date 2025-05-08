package org.azauner.minicpp.bytecode

import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.bytecode.expr.ActionFactGenerator
import org.azauner.minicpp.bytecode.field.StaticConstDefGenerator
import org.azauner.minicpp.bytecode.field.StaticVarDefGenerator
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes.*

class MiniCppGenerator(private val miniCpp: org.azauner.minicpp.ast.node.MiniCpp) {

    companion object {
        private const val CLASS_FILE_VERSION = 65
        var scannerVarName = "scanner"
        lateinit var className: String
    }

    fun generateByteCode(): ByteArray {
        val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)
        className = miniCpp.className
        classWriter.visit(
            CLASS_FILE_VERSION,
            ACC_PUBLIC,
            miniCpp.className,
            null,
            "java/lang/Object",
            null
        )

        val staticVarDefGenerator = StaticVarDefGenerator(classWriter)
        miniCpp.entries.forEach {
            when (it) {
                is VarDef -> staticVarDefGenerator.generateStatic(it)
                is ConstDef -> StaticConstDefGenerator(classWriter).generateStatic(it)
                is FuncDef -> FuncDefGenerator(classWriter, miniCpp.className).generate(it)
                is Sem, is FuncDecl -> ""
            }
        }
        staticVarDefGenerator.generateStaticInitBlock(miniCpp)
        addStaticScannerField(classWriter)
        addMainMethod(classWriter)
        classWriter.visitEnd()

        return classWriter.toByteArray()
    }

    private fun addMainMethod(classWriter: ClassWriter) {
        val mainIdent = org.azauner.minicpp.ast.node.Ident("main")
        miniCpp.globalScope.getFunction(mainIdent, listOf(org.azauner.minicpp.ast.node.ExprType.VOID))

        classWriter.visitMethod(
            ACC_PUBLIC or ACC_STATIC,
            mainIdent.name,
            "([Ljava/lang/String;)V",
            null,
            null
        ).apply {
            visitCode()
            val descriptor = ActionFactGenerator.getDescriptor(listOf(), org.azauner.minicpp.ast.node.ExprType.VOID)
            visitMethodInsn(INVOKESTATIC, className, mainIdent.name, descriptor, false)
            visitInsn(RETURN)
            visitMaxs(0, 0)
            visitEnd()
        }
    }

    private fun addStaticScannerField(classWriter: ClassWriter) {
        classWriter.visitField(
            ACC_PUBLIC or ACC_STATIC,
            "scanner",
            "Ljava/util/Scanner;",
            null,
            null
        )
    }
}
