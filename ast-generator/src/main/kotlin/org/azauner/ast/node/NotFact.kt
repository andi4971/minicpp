package org.azauner.ast.node

data class NotFact(val negated: Boolean, val fact: Fact)
