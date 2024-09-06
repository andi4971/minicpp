package org.azauner.ast.generator

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import java.nio.file.Path

fun writeClassNodeToFile(node: ClassNode, path: Path) {
    val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES or ClassWriter.COMPUTE_MAXS)
    node.accept(classWriter)
    val bytes = classWriter.toByteArray()
    path.toFile().writeBytes(bytes)
}
