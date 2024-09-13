package org.azauner.minicpp.bytecode.stat

import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.ast.util.getType
import org.azauner.minicpp.bytecode.expr.ExprGenerator
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.GETSTATIC
import org.objectweb.asm.Opcodes.INVOKEVIRTUAL

class OutputStatGenerator(private val mv: MethodVisitor) {

    companion object {
        const val SYSTEM_QUALIFIED_NAME = "java/lang/System"
        const val OUT_FIELD_NAME = "out"
        const val PRINT_STREAM_QUALIFIED_NAME = "java/io/PrintStream"
        const val OUT_FIELD_DESC = "L$PRINT_STREAM_QUALIFIED_NAME;"
        const val PRINTLN_METHOD_NAME = "println"
        const val PRINT_METHOD_NAME = "print"
        const val PRINTLN_METHOD_DESC = "()V"
        const val PRINT_STRING_DESC = "(Ljava/lang/String;)V"
        const val PRINT_INT_DESC = "(I)V"
        const val PRINT_BOOL_DESC = "(Z)V"
    }

    fun generate(stat: OutputStat) {
        stat.entries.forEach { entry ->
            loadPrintlnOutField()
            when (entry) {
                Endl -> generatePrintln()
                is Expr -> generatePrintExpr(entry)
                is Text -> generatePrintText(entry.text)
            }
        }
    }

    private fun generatePrintExpr(expr: Expr) {
        ExprGenerator(mv).generate(expr)
        val descriptor = if(expr.getType() == ExprType.INT) {
            PRINT_INT_DESC
        }else {
            PRINT_BOOL_DESC
        }
        generatePrint(descriptor)
    }

    private fun generatePrintText(text: String) {
        mv.visitLdcInsn(text)
        generatePrint(PRINT_STRING_DESC)
    }

    private fun generatePrint(descriptor: String) {
        mv.visitMethodInsn(
            INVOKEVIRTUAL,
            PRINT_STREAM_QUALIFIED_NAME,
            PRINT_METHOD_NAME,
            descriptor,
            false
        )
    }

    private fun generatePrintln() {
        mv.visitMethodInsn(
            INVOKEVIRTUAL,
            PRINT_STREAM_QUALIFIED_NAME,
            PRINTLN_METHOD_NAME,
            PRINTLN_METHOD_DESC,
            false
        )
    }

    private fun loadPrintlnOutField() {
        mv.visitFieldInsn(GETSTATIC, SYSTEM_QUALIFIED_NAME, OUT_FIELD_NAME, OUT_FIELD_DESC)
    }
}
