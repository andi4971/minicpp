package org.azauner.minicpp.bytecode

import org.azauner.minicpp.ast.node.FormParListEntries
import org.azauner.minicpp.ast.node.FuncHead

fun FuncHead.getDescriptor(): String {
    val descriptor = StringBuilder("(")
    if (formParList != null && formParList is FormParListEntries) {
        (formParList as FormParListEntries).entries.forEach {
            descriptor.append(it.type.descriptor)
        }
    }
    descriptor.append(")V")
    //descriptor.append(type.descriptor)
    return descriptor.toString()
}
