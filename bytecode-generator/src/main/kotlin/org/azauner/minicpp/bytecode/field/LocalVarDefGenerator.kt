package org.azauner.minicpp.bytecode.field

import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.ast.util.toPointerTypeOptional
import org.azauner.minicpp.bytecode.pushBoolValue
import org.azauner.minicpp.bytecode.pushIntValue
import org.azauner.minicpp.bytecode.pushNullValue
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ASTORE
import org.objectweb.asm.Opcodes.ISTORE

class LocalVarDefGenerator(private val mv: MethodVisitor) {

    fun generate(varDef: VarDef) {
        varDef.entries.forEach { entry ->
            val type = varDef.type.toPointerTypeOptional(entry.pointer)
            pushInitValue(entry.value, type)
            storeVariable(type, entry.variable.index)
        }
    }

    private fun storeVariable(type: ExprType, index: Int) {
        val opCode = when (type) {
            ExprType.INT -> ISTORE
            ExprType.BOOL -> ISTORE
            else -> ASTORE
        }
        mv.visitVarInsn(opCode, index)
    }

    private fun pushInitValue(init: Init?, type: ExprType) {
        if(init != null) {
            when(init.value) {
                is BoolType -> mv.pushBoolValue((init.value as BoolType).value)
                is IntType -> mv.pushIntValue((init.value as IntType).value)
                NullPtrType -> mv.pushNullValue()
            }
        }else {
            when (type) {
                ExprType.INT -> mv.pushBoolValue(false)
                ExprType.BOOL -> mv.pushIntValue(0)
                else -> mv.pushNullValue()
            }
        }
    }

}


