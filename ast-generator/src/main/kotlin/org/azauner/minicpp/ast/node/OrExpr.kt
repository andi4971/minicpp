package org.azauner.minicpp.ast.node

import org.azauner.minicpp.ast.node.scope.Scope


data class OrExpr(val andExpressions: List<AndExpr>,val  scope: Scope)
