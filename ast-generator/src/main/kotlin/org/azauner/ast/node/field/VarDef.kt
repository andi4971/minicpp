package org.azauner.ast.node.field

import org.azauner.ast.node.Type

//todo check type on validation
data class VarDef(val type: Type, val entries: List<VarDefEntry>): BlockEntry
