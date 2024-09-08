package org.azauner.minicpp.ast.generator.visitor.block
import org.azauner.minicpp.ast.node.Block
import org.azauner.minicpp.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class BlockVisitor(private val scope: Scope) : minicppBaseVisitor<Block>() {
    override fun visitBlock(ctx: minicppParser.BlockContext): Block {
        return Block(
            ctx.blockEntry().map { it.accept(BlockEntryVisitor(scope)) },
            scope = scope
        )
    }
}
