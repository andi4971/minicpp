package org.azauner.minicpp.bytecode.stat

import org.azauner.minicpp.ast.util.getType
import org.azauner.minicpp.bytecode.BlockGenerator
import org.azauner.minicpp.bytecode.MiniCppGenerator.Companion.scannerVarName
import org.azauner.minicpp.bytecode.expr.ExprGenerator
import org.azauner.minicpp.bytecode.field.SCANNER_DESC
import org.azauner.minicpp.bytecode.field.SCANNER_QUAL_NAME
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.GETSTATIC

class StatGenerator(val mv: MethodVisitor, private val className: String) {


    fun generate(stat: org.azauner.minicpp.ast.node.Stat, breakLabel: Label?) {
        when (stat) {
            is org.azauner.minicpp.ast.node.InputStat -> generateInputStat(stat)
            is org.azauner.minicpp.ast.node.BlockStat -> BlockGenerator(mv, className).generate(stat.block, breakLabel)
            is org.azauner.minicpp.ast.node.DeleteStat -> generateDeleteStat(stat)
            is org.azauner.minicpp.ast.node.ReturnStat -> generateReturnStat(stat)
            is org.azauner.minicpp.ast.node.OutputStat -> OutputStatGenerator(mv).generate(stat)
            is org.azauner.minicpp.ast.node.ExprStat -> ExprGenerator(mv).generate(stat.expr, false)
            is org.azauner.minicpp.ast.node.WhileStat -> generateWhileStat(stat)
            is org.azauner.minicpp.ast.node.IfStat -> generateIfStat(stat, breakLabel)
            is org.azauner.minicpp.ast.node.BreakStat -> mv.visitJumpInsn(Opcodes.GOTO, breakLabel!!)
            is org.azauner.minicpp.ast.node.EmptyStat -> ""
        }
    }

    private fun generateIfStat(stat: org.azauner.minicpp.ast.node.IfStat, breakLabel: Label?) {
        val elseLabel = Label()
        val endLabel = Label()

        ExprGenerator(mv).generate(stat.condition)
        mv.visitJumpInsn(Opcodes.IFEQ, elseLabel)
        generate(stat.thenStat, breakLabel)
        mv.visitJumpInsn(Opcodes.GOTO, endLabel)
        mv.visitLabel(elseLabel)
        stat.elseStat?.let { generate(it, breakLabel) }
        mv.visitLabel(endLabel)
    }

    private fun generateWhileStat(stat: org.azauner.minicpp.ast.node.WhileStat) {
        val startLabel = Label()
        val endLabel = Label()
        mv.visitLabel(startLabel)
        ExprGenerator(mv).generate(stat.condition)
        mv.visitJumpInsn(Opcodes.IFEQ, endLabel)
        generate(stat.whileStat, endLabel)
        mv.visitJumpInsn(Opcodes.GOTO, startLabel)
        mv.visitLabel(endLabel)
    }


    private fun generateReturnStat(stat: org.azauner.minicpp.ast.node.ReturnStat) {
        val returnCode = stat.expr?.let {
            when (it.getType()) {
                org.azauner.minicpp.ast.node.ExprType.INT, org.azauner.minicpp.ast.node.ExprType.BOOL -> Opcodes.IRETURN
                else -> Opcodes.ARETURN
            }

        } ?: Opcodes.RETURN
        stat.expr?.let { ExprGenerator(mv).generate(it) }
        mv.visitInsn(returnCode)
    }

    private fun generateDeleteStat(stat: org.azauner.minicpp.ast.node.DeleteStat) {
        val variable = stat.scope.getVariable(stat.ident)
        mv.visitInsn(Opcodes.ACONST_NULL)
        mv.visitVarInsn(Opcodes.ASTORE, variable.index)
    }

    private fun generateInputStat(stat: org.azauner.minicpp.ast.node.InputStat) {
        mv.visitFieldInsn(GETSTATIC, className, scannerVarName, SCANNER_DESC)
        val variable = stat.scope.getVariable(stat.ident)

        if (variable.type == org.azauner.minicpp.ast.node.ExprType.INT) {
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
        if(variable.static) {
            mv.visitFieldInsn(
                Opcodes.PUTSTATIC,
                className,
                variable.ident.name,
                variable.type.descriptor
            )
        } else {
            mv.visitVarInsn(Opcodes.ISTORE, variable.index)
        }
    }
}
