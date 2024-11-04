package org.azauner.minicpp.bytecode.expr

import org.azauner.minicpp.bytecode.pushBoolValue
import org.azauner.minicpp.bytecode.pushIntValue
import org.azauner.minicpp.bytecode.pushNullValue
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class FactGenerator(private val mv: MethodVisitor) {

    fun generate(fact: org.azauner.minicpp.ast.node.Fact, shouldEmitValue: Boolean = true) {
        when {
            fact is org.azauner.minicpp.ast.node.ActionFact -> ActionFactGenerator(mv).generate(fact, shouldEmitValue)
            fact is org.azauner.minicpp.ast.node.BoolType && shouldEmitValue -> mv.pushBoolValue(fact.value)
            fact is org.azauner.minicpp.ast.node.IntType && shouldEmitValue -> mv.pushIntValue(fact.value)
            fact is org.azauner.minicpp.ast.node.NullPtrType && shouldEmitValue -> mv.pushNullValue()
            fact is org.azauner.minicpp.ast.node.ExprFact -> ExprGenerator(mv).generate(fact.expr, true)
            fact is org.azauner.minicpp.ast.node.NewArrayTypeFact -> generateNewArray(fact)
            else -> null
        }
    }

    private fun generateNewArray(fact: org.azauner.minicpp.ast.node.NewArrayTypeFact) {
        val arrayType = if (fact.type == org.azauner.minicpp.ast.node.ExprType.INT) {
            T_INT
        } else {
            T_BOOLEAN
        }

        //size
        ExprGenerator(mv).generate(fact.expr)

        mv.visitIntInsn(NEWARRAY, arrayType)

    }
}
