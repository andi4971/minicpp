package org.azauner.ast.node

data class FuncHead(val type: ExprType, val ident: Ident, val formParList: FormParList?){

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
