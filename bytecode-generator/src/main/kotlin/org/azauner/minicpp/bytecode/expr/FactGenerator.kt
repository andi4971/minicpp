package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.bytecode.pushBoolValue
import org.azauner.minicpp.bytecode.pushIntValue
import org.azauner.minicpp.bytecode.pushNullValue
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class FactGenerator(private val mv: MethodVisitor) {

    fun generate(fact: Fact) {
        when(fact) {
            is ActionFact -> ActionFactGenerator(mv).generate(fact)
            is BoolType -> mv.pushBoolValue(fact.value)
            is IntType -> mv.pushIntValue(fact.value)
            NullPtrType -> mv.pushNullValue()
            is ExprFact -> ExprGenerator(mv).generate(fact.expr)
            is NewArrayTypeFact -> generateNewArray(fact)
        }

    }

    private fun generateNewArray(fact: NewArrayTypeFact) {
        val arrayType = if(fact.type == ExprType.INT) {
            T_INT
        } else {
            T_BOOLEAN
        }

        //size
        ExprGenerator(mv).generate(fact.expr)

        mv.visitIntInsn(NEWARRAY, arrayType)

    }
}
