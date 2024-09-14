package org.azauner.minicpp.bytecode.stat

import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.ast.util.getType
import org.azauner.minicpp.bytecode.BlockGenerator
import org.azauner.minicpp.bytecode.MiniCppGenerator.Companion.scannerVarName
import org.azauner.minicpp.bytecode.expr.ExprGenerator
import org.azauner.minicpp.bytecode.field.SCANNER_DESC
import org.azauner.minicpp.bytecode.field.SCANNER_QUAL_NAME
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.GETSTATIC

class StatGenerator(val mv: MethodVisitor, private val className: String) {

    fun generate(stat: Stat) {
        when (stat) {
            is InputStat -> generateInputStat(stat)
            is BlockStat -> BlockGenerator(mv, className).generate(stat.block)
            is DeleteStat -> generateDeleteStat(stat)
            is ReturnStat -> generateReturnStat(stat)
            is OutputStat -> OutputStatGenerator(mv).generate(stat)
            is ExprStat -> ExprGenerator(mv).generate(stat.expr)
            else -> ""
            /* is
            BreakStat -> TODO()
            EmptyStat -> TODO()
            is IfStat -> TODO()
            is WhileStat -> TODO()*/
        }
    }


    private fun generateReturnStat(stat: ReturnStat) {
        val returnCode = stat.expr?.let {
            when (it.getType()) {
                ExprType.INT, ExprType.BOOL -> Opcodes.IRETURN
                else -> Opcodes.ARETURN
            }

        } ?: Opcodes.RETURN
        mv.visitInsn(returnCode)
    }

    private fun generateDeleteStat(stat: DeleteStat) {
        val variable = stat.scope.getVariable(stat.ident)
        mv.visitInsn(Opcodes.ACONST_NULL)
        mv.visitVarInsn(Opcodes.ASTORE, variable.index)
    }

    private fun generateInputStat(stat: InputStat) {
        mv.visitFieldInsn(GETSTATIC, className, scannerVarName, SCANNER_DESC)
        val variable = stat.scope.getVariable(stat.ident)

        if (variable.type == ExprType.INT) {
            mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                SCANNER_QUAL_NAME,
                "nextInt",
                "()I",
                false
            )
        } else {
            mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                SCANNER_QUAL_NAME,
                "nextBoolean",
                "()Z",
                false
            )
        }
        mv.visitVarInsn(Opcodes.ISTORE, variable.index)
    }
}
