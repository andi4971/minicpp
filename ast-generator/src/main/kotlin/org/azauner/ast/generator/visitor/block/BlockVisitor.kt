package org.azauner.ast.generator.visitor.block
import org.azauner.ast.node.Block
import org.azauner.ast.node.scope.Scope
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
