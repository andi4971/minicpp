package org.azauner.minicpp.ast.generator

import org.azauner.minicpp.ast.node.*

fun countMiniCppEntries(miniCpp: MiniCpp): Int {
    return 1 + countMiniCppEntries(miniCpp.entries)
}

fun countMiniCppEntries(entries: List<MiniCppEntry>): Int {
    return entries.sumOf { countMiniCppEntry(it) }
}

fun countMiniCppEntry(entry: MiniCppEntry): Int {
    return when (entry) {
        is ConstDef -> countConstDef(entry)
        is FuncDecl -> countFuncDecl(entry)
        is FuncDef -> countFuncDef(entry)
        Sem -> 0
        is VarDef -> countVarDef(entry)
    }
}

fun countFuncDef(entry: FuncDef): Int {
    var count = 1
    count += countFuncHead(entry.funHead)
    count += countBlock(entry.block)
    return count
}

fun countBlock(block: Block): Int {
    var count = 1
    count += block.entries.sumOf { countBlockEntry(it) }
    return count
}

fun countBlockEntry(it: BlockEntry): Int {
    return when (it) {
        is ConstDef -> countConstDef(it)
        is Stat -> countStat(it)
        is VarDef -> countVarDef(it)
    }
}

fun countStat(it: Stat): Int {
    return when (it) {
        is BlockStat -> countBlockStat(it)
        BreakStat -> 0
        is DeleteStat -> countDeleteStat(it)
        EmptyStat -> 0
        is ExprStat -> countExprStat(it)
        is IfStat -> countIfStat(it)
        is InputStat -> countInputStat(it)
        is OutputStat -> countOutputStat(it)
        is ReturnStat -> countReturnStat(it)
        is WhileStat -> countWhileStat(it)
    }
}

fun countOutputStat(it: OutputStat): Int {
    return 1 + it.entries.sumOf { countOutputStatEntry(it) }
}

fun countOutputStatEntry(it: OutputStatEntry): Int {
    return when (it) {
        is Endl -> 0
        is Expr -> countExpr(it)
        is Text -> 1
    }
}

fun countIfStat(it: IfStat): Int {
    return 1 + countExpr(it.condition) + countStat(it.thenStat) + (it.elseStat?.let { countStat(it) } ?: 0)
}

fun countExprStat(it: ExprStat): Int {
    return 1 + countExpr(it.expr)
}

fun countWhileStat(it: WhileStat): Int {
    return 1 + countExpr(it.condition) + countStat(it.whileStat)
}

fun countReturnStat(it: ReturnStat): Int {
    return 1 + (it.expr?.let { countExpr(it) } ?: 0)
}

fun countExpr(it: Expr): Int {
    var count = 1
    count += countOrExpr(it.firstExpr)
    count += it.exprEntries.sumOf { countExprEntry(it) }
    return count
}

fun countOrExpr(firstExpr: OrExpr): Int {
    var count = 1
    count += firstExpr.andExpressions.sumOf { countAndExpr(it) }
    return count
}

fun countAndExpr(it: AndExpr): Int {
    var count = 1
    count += it.relExpressions.sumOf { countRelExpr(it) }
    return count
}

fun countRelExpr(it: RelExpr): Int {
    var count = 1
    count += countSimpleExpr(it.firstExpr)
    count += it.relExprEntries.sumOf { countRelExprEntry(it) }
    return count
}

fun countSimpleExpr(firstExpr: SimpleExpr): Int {
    var count = 1
    count += countTerm(firstExpr.term)
    firstExpr.sign?.let {
        count += 1
    }
    count += firstExpr.simpleExprEntries.sumOf { countSimpleExprEntry(it) }
    return count
}

fun countSimpleExprEntry(it: SimpleExprEntry): Int {
    var count = 1
    //sign
    count += 1
    count += countTerm(it.term)
    return count
}

fun countTerm(term: Term): Int {
    var count = 1
    count += countNotFact(term.firstNotFact)
    count += term.termEntries.sumOf { countTermEntry(it) }
    return count
}

fun countTermEntry(it: TermEntry): Int {
    var count = 1
    //mul op
    count += 1
    count += countNotFact(it.notFact)
    return count
}

fun countNotFact(firstNotFact: NotFact): Int {
    var count = 1
    //negation
    count += 1
    count += countFact(firstNotFact.fact)
    return count
}

fun countFact(fact: Fact): Int {
    return when (fact) {
        is ActionFact -> countActionFact(fact)
        is BoolType,
        is IntType -> 1

        NullPtrType -> 0
        is ExprFact -> 1 + countExpr(fact.expr)
        is NewArrayTypeFact -> 1 + 1 + countExpr(fact.expr)
    }
}

fun countActionFact(fact: ActionFact): Int {
    var count = 1
    count += countIdent(fact.ident)
    fact.prefix?.let {
        count += 1
    }
    fact.suffix?.let {
        count += 1
    }
    fact.actionOp?.let {
        count += countActionOp(it)
    }
    return count
}

fun countActionOp(it: ActionOperation): Int {
    return when (it) {
        is ArrayAccessOperation -> 1 + countExpr(it.expr)
        is CallOperation -> 1 + countActParList(it.actParList)
    }
}

fun countActParList(actParList: List<Expr>): Int {
    return actParList.sumOf { countExpr(it) }
}

fun countRelExprEntry(it: RelExprEntry): Int {
    var count = 1
    //rel op
    count += 1
    count += countSimpleExpr(it.simpleExpr)
    return count
}

fun countExprEntry(it: ExprEntry): Int {
    var count = 1
    //assing op
    count += 1
    count += countOrExpr(it.orExpr)
    return count
}

fun countInputStat(it: InputStat): Int {
    return 1 + countIdent(it.ident)
}

fun countDeleteStat(it: DeleteStat): Int {
    return 1 + countIdent(it.ident)
}

fun countBlockStat(it: BlockStat): Int {
    return 1 + countBlock(it.block)
}

fun countVarDef(entry: VarDef): Int {
    var count = 1
    //type
    count += 1
    count += entry.entries.sumOf { countVarDefEntry(it) }
    return count
}

fun countVarDefEntry(it: VarDefEntry): Int {
    var count = 1
    it.value?.let {
        count += countInit(it)
    }
    //pointer
    //TODO
    count += 1
    count += countIdent(it.ident)
    return count
}

fun countFuncDecl(entry: FuncDecl): Int {
    var count = 1
    count += countFuncHead(entry.funcHead)
    return count
}

fun countFuncHead(funcHead: FuncHead): Int {
    var count = 1
    //type
    count += 1
    count += countIdent(funcHead.ident)

    funcHead.formParList?.let {
        count += countFormParList(it)
    }
    return count
}

fun countFormParList(formParList: FormParList): Int {
    var count = 1
    return when (formParList) {
        is FormParListEntries -> count + formParList.entries.sumOf { countFormpartListEntry(it) }
        VoidFormParListChild -> 0
    }

}

fun countFormpartListEntry(it: FormParListEntry): Int {
    var count = 1
    //type
    count += 1
    count += countIdent(it.ident)
    return count
}

fun countIdent(ident: Ident): Int {
    //self + name
    return 1
}

fun countConstDef(entry: ConstDef): Int {
    //self
    var count = 1
    //type
    count += 1

    count += entry.entries.sumOf { 1 + countInit(it.value) }
    return count
}

fun countInit(value: Init): Int {
    var count = 1
    count += 1
    return count
}
