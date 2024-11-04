package org.azauner.minicpp.ast.generator.visitor.block
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class BlockVisitor(private val scope: org.azauner.minicpp.ast.node.scope.Scope) :
    minicppBaseVisitor<org.azauner.minicpp.ast.node.Block>() {
    override fun visitBlock(ctx: minicppParser.BlockContext): org.azauner.minicpp.ast.node.Block {
        return org.azauner.minicpp.ast.node.Block(
            ctx.blockEntry().map { it.accept(BlockEntryVisitor(scope)) },
            scope = scope
        )
    }
}
