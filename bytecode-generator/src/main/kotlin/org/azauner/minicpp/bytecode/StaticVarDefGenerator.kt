package org.azauner.minicpp.bytecode

import org.azauner.minicpp.ast.node.VarDef
import org.azauner.minicpp.ast.util.toPointerTypeOptional
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

class StaticVarDefGenerator(private val cw: ClassWriter, private val className: String) {

    private val generatedVarDefs = mutableListOf<VarDef>()

    fun generateStatic(varDef: VarDef) {
        varDef.entries.forEach { entry ->
            cw.visitField(
                Opcodes.ACC_PUBLIC
                        or Opcodes.ACC_STATIC,
                entry.ident.name,
                varDef.type.toPointerTypeOptional(entry.pointer).descriptor,
                null,
                entry.value?.value?.getValue()
            )
        }
        generatedVarDefs.add(varDef)
    }

    fun generateStaticInitBlock() {
        cw.visitMethod(
            Opcodes.ACC_STATIC,
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
                        Opcodes.PUTSTATIC,
                        className,
                        entry.ident.name,
                        varDef.type.toPointerTypeOptional(entry.pointer).descriptor
                    )
                }
            }
            visitInsn(Opcodes.RETURN)
            visitMaxs(0, 0)
            visitEnd()
        }
    }

    /*fun getFieldNode(isStatic: Boolean): List<FieldNode> {
        return varDef.entries.map { entry ->
            FieldNode(
                Opcodes.ACC_PUBLIC
                        or (if(isStatic) Opcodes.ACC_STATIC else 0),
                entry.ident.name,
                varDef.type.descriptor,
                null,
                entry.value?.value?.getValue()
            )
        }
    }
*/
}
