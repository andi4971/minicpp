package org.azauner.ast.node

import org.azauner.ast.SourceCodeGenerator

data class FuncHead(val type: ExprType, val ident: Ident, val formParList: FormParList?): SourceCodeGenerator{
    override fun generateSourceCode(sb: StringBuilder) {
        type.generateSourceCode(sb)
        sb.append(" ")
        sb.append(ident.name)
        sb.append("(")
        formParList?.generateSourceCode(sb)
        sb.append(")")
    }

    fun getDescriptor(): String {
        val descriptor = StringBuilder("(")
        if (formParList != null && formParList is FormParListEntries) {
            formParList.entries.forEach {
                descriptor.append(it.type.descriptor)
            }
        }
        descriptor.append(")V")
        //descriptor.append(type.descriptor)
        return descriptor.toString()
    }

}
