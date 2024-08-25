package org.azauner.ast.generator.visitor.block
import org.azauner.ast.node.Block
import org.azauner.ast.node.scope.Scope
import org.azauner.parser.minicppBaseVisitor
import org.azauner.parser.minicppParser

class BlockVisitor(private val parentScope: Scope) : minicppBaseVisitor<Block>() {
    override fun visitBlock(ctx: minicppParser.BlockContext): Block {
        val scope = Scope(parentScope)
        return Block(
            ctx.blockEntry().map { it.accept(BlockEntryVisitor(scope)) },
            scope = scope
        )
    }
}
