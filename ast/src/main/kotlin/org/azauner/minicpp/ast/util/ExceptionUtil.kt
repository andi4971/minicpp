package org.azauner.minicpp.ast.util

import org.azauner.minicpp.ast.generator.exception.SemanticException

fun requireSemantic(condition: Boolean, lazyMessage: () -> Any) {
    if (!condition) {
        throw SemanticException(lazyMessage().toString())
    }
}
