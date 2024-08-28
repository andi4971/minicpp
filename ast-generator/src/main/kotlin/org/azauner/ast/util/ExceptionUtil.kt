package org.azauner.ast.util

import org.azauner.ast.generator.exception.SemanticException

fun requireSemantic(condition: Boolean, lazyMessage: () -> Any) {
    if (!condition) {
        throw SemanticException(lazyMessage().toString())
    }
}
