package org.azauner.minicpp.bytecode.field

import org.azauner.minicpp.ast.util.toPointerTypeOptional
import org.azauner.minicpp.bytecode.pushBoolValue
import org.azauner.minicpp.bytecode.pushIntValue
import org.azauner.minicpp.bytecode.pushNullValue
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ASTORE
import org.objectweb.asm.Opcodes.ISTORE

class LocalVarDefGenerator(private val mv: MethodVisitor) {

    fun generate(varDef: org.azauner.minicpp.ast.node.VarDef) {
        varDef.entries.forEach { entry ->
            val type = varDef.type.toPointerTypeOptional(entry.pointer)
            pushInitValue(entry.value, type)
            storeVariable(type, entry.variable.index)
        }
    }

    private fun storeVariable(type: org.azauner.minicpp.ast.node.ExprType, index: Int) {
        val opCode = when (type) {
            org.azauner.minicpp.ast.node.ExprType.INT -> ISTORE
            org.azauner.minicpp.ast.node.ExprType.BOOL -> ISTORE
            else -> ASTORE
        }
        mv.visitVarInsn(opCode, index)
    }

    private fun pushInitValue(init: org.azauner.minicpp.ast.node.Init?, type: org.azauner.minicpp.ast.node.ExprType) {
        if(init != null) {
            when(init.value) {
                is org.azauner.minicpp.ast.node.BoolType -> mv.pushBoolValue((init.value as org.azauner.minicpp.ast.node.BoolType).value)
                is org.azauner.minicpp.ast.node.IntType -> mv.pushIntValue((init.value as org.azauner.minicpp.ast.node.IntType).value)
                org.azauner.minicpp.ast.node.NullPtrType -> mv.pushNullValue()
            }
        }else {
            when (type) {
                org.azauner.minicpp.ast.node.ExprType.INT -> mv.pushBoolValue(false)
                org.azauner.minicpp.ast.node.ExprType.BOOL -> mv.pushIntValue(0)
                else -> mv.pushNullValue()
            }
        }
    }

}


