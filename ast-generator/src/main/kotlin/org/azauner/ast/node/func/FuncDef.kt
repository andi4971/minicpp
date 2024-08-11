package org.azauner.ast.node.func

import org.azauner.ast.node.field.Block

data class FuncDef(val funHead:FuncHead, val block: Block) {
}
