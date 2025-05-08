package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.ast.node.*
import org.azauner.minicpp.bytecode.pushBoolValue
import org.azauner.minicpp.bytecode.pushIntValue
import org.azauner.minicpp.bytecode.pushNullValue
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class FactGenerator(private val mv: MethodVisitor) {

    fun generate(fact: Fact, shouldEmitValue: Boolean = true) {
        when {
            fact is ActionFact -> ActionFactGenerator(mv).generate(fact, shouldEmitValue)
            fact is BoolType && shouldEmitValue -> mv.pushBoolValue(fact.value)
            fact is IntType && shouldEmitValue -> mv.pushIntValue(fact.value)
            fact is NullPtrType && shouldEmitValue -> mv.pushNullValue()
            fact is ExprFact -> ExprGenerator(mv).generate(fact.expr, true)
            fact is NewArrayTypeFact -> generateNewArray(fact)
        }
    }

    private fun generateNewArray(fact: NewArrayTypeFact) {
        val arrayType = if (fact.type == ExprType.INT) {
            T_INT
        } else {
            T_BOOLEAN
        }
        ExprGenerator(mv).generate(fact.expr)
        mv.visitIntInsn(NEWARRAY, arrayType)
    }
}
