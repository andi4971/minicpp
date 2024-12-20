grammar minicppAtg;


@header {

import java.util.*;
import org.azauner.minicpp.ast.node.*;
import org.azauner.minicpp.ast.node.scope.Scope;
import org.azauner.minicpp.ast.generator.exception.SemanticException;
import org.azauner.minicpp.ast.util.ScopeHandler;

}

@parser::members {

 public MiniCpp result;
    public String className;

    private ScopeHandler scopeHandler = new ScopeHandler();

 	private Stack<MiniCppEntry> miniCppEntries = new Stack<MiniCppEntry>();

 	private Stack<ConstDef> constDefs = new Stack<ConstDef>();
    private Stack<ConstDefEntry> constDefEntries = new Stack<ConstDefEntry>();
    private Stack<Init> inits = new Stack<Init>();
    private Stack<ExprType> types = new Stack<ExprType>();

    private Stack<VarDef> varDefs = new Stack<VarDef>();
    private Stack<VarDefEntry> varDefEntries = new Stack<VarDefEntry>();

    private Stack<FuncDecl> funcDecls = new Stack<FuncDecl>();
    private Stack<FuncHead> funcHeads = new Stack<FuncHead>();
    private Stack<FuncDef> funcDefs = new Stack<FuncDef>();
    private Stack<FormParList> formParLists = new Stack<FormParList>();
    private Stack<FormParListEntry> formParListEntries = new Stack<FormParListEntry>();

    private Stack<Block> blocks = new Stack<Block>();
    private Stack<BlockEntry> blockEntries = new Stack<BlockEntry>();

    private Stack<Stat> stats = new Stack<Stat>();
    private Stack<OutputStatEntry> outputStatEntries = new Stack<OutputStatEntry>();

    private Stack<Expr> exprs = new Stack<Expr>();
    private Stack<ExprEntry> exprEntries = new Stack<ExprEntry>();
    private Stack<AssignOperator> assignOperators = new Stack<AssignOperator>();
    private Stack<OrExpr> orExprs = new Stack<OrExpr>();
    private Stack<AndExpr> andExprs = new Stack<AndExpr>();
    private Stack<RelExpr> relExprs = new Stack<RelExpr>();
    private Stack<RelExprEntry> relExprEntries = new Stack<RelExprEntry>();
    private Stack<RelOperator> relOperators = new Stack<RelOperator>();
    private Stack<SimpleExpr> simpleExprs = new Stack<SimpleExpr>();
    private Stack<SimpleExprEntry> simpleExprEntries = new Stack<SimpleExprEntry>();

    private Stack<Term> terms = new Stack<Term>();
    private Stack<NotFact> notFacts = new Stack<NotFact>();
    private Stack<Fact> facts = new Stack<Fact>();
    private Stack<TermEntry> termEntries = new Stack<TermEntry>();
    private Stack<TermOperator> termOperators = new Stack<TermOperator>();
    private Stack<ActionFact> actionFacts = new Stack<ActionFact>();
    private Stack<ActionOperation> actionOperations = new Stack<ActionOperation>();
    private Stack<List<Expr>> actParLists = new Stack<List<Expr>>();

   	private <T> List<T> getElementsFromStack(Stack<T> stack, int n) {
   		List<T> elements = new ArrayList<T>();
   		for (int i = 0; i < n; i++) {
   			elements.add(stack.pop());
   		}
   		return elements;
   	}

   private ExprType toArrayTypeOptional(ExprType type, boolean isArray) {
   	    if (isArray) {
   	        switch (type) {
   	            case INT:
   	                return ExprType.INT_ARR;
   	            case BOOL:
   	                return ExprType.BOOL_ARR;
   	            default:
   	                throw new SemanticException("Unsupported array type: " + type);
   	        }
   	    }
   	    return type;
   	}

   	public String formatString(String s) {
    			   return s.substring(1, s.length() - 1)
    					   .replace("\\\\", "\\")
    					   .replace("\\t", "\t")
    					   .replace("\\n", "\n")
    					   .replace("\\r", "\r")
    					   .replace("\\\"", "\"");
    	}
}


miniCpp: (miniCppEntry)* EOF                    { result = new MiniCpp(className, scopeHandler.getScope(), miniCppEntries.stream().toList()); }
                ;
miniCppEntry:     constDef                      { miniCppEntries.push(constDefs.pop()); }
                | varDef                        { miniCppEntries.push(varDefs.pop()); }
                | funcDecl                      { miniCppEntries.push(funcDecls.pop()); }
                | funcDef                       { miniCppEntries.push(funcDefs.pop()); }
                | SEM                           { miniCppEntries.push(Sem.INSTANCE); }
;
constDef:    CONST type constDefEntry           { var entries = new ArrayList(List.of(constDefEntries.pop())); }
                (
                    ',' constDefEntry           { entries.add(constDefEntries.pop());}
                )
                * SEM
                                                { constDefs.push(new ConstDef(types.pop(), entries)); }
;
constDefEntry: IDENT init
                                                {
                                                    var value = inits.peek().getValue().getValue();
                                                    var variable = scopeHandler.getScope().addVariable(new Ident($IDENT.text), types.peek(), true,value );
                                                    constDefEntries.push(new ConstDefEntry(new Ident($IDENT.text), inits.pop(), variable));
                                                }
;
init:        '='  initOption;
initOption:    BOOLEAN                          { inits.push(new Init(new BoolType(Boolean.parseBoolean($BOOLEAN.text)))); }  #BooleanInit
             | NULLPTR                          { inits.push(new Init(NullPtrType.INSTANCE)); }                               #NullptrInit
             | (SIGN)? INT                      {
                                                     var value = Integer.parseInt($INT.text);
                                                     if ($SIGN != null && $SIGN.text.equals("-")) {
                                                         value = -value;
                                                     }
                                                     inits.push(new Init(new IntType(value)));
                                                }                                                                             #IntInit
;

varDef:      type varDefEntry                   { var entries = new ArrayList(List.of(varDefEntries.pop())); }
             (
                ',' varDefEntry                 { entries.add(varDefEntries.pop()); }
             )* SEM
                                                { varDefs.push(new VarDef(types.pop(), entries)); }
;
varDefEntry: STAR? IDENT (init)?
                                                {
                                                  Object value = $init.text != null ? inits.peek().getValue().getValue(): null;
                                                  var variable = scopeHandler.getScope().addVariable(new Ident($IDENT.text), toArrayTypeOptional(types.peek(), $STAR != null), false, value);
                                                  varDefEntries.push(new VarDefEntry(new Ident($IDENT.text), $STAR != null, ($init.text != null ? inits.pop(): null), variable));
                                                }
;
funcDecl:    funcHead SEM
                                                {
                                                  var funcHead = funcHeads.pop();
                                                  funcDecls.push(new FuncDecl(funcHead));
                                                  scopeHandler.getScope().addFunction(funcHead.getIdent(), funcHead.getType(), funcHead.getFormParList(), false);
                                                }
;
funcDef:     funcHead
                                                {
                                                  scopeHandler.pushChildScope();
                                                  var funcHead = funcHeads.pop();
                                                  var scope = scopeHandler.getScope();
                                                  if(funcHead.getFormParList() instanceof FormParListEntries) {
                                                      var entries = ((FormParListEntries) funcHead.getFormParList()).getEntries();
                                                      for (FormParListEntry entry : entries) {
                                                          scope.addVariable(entry.getIdent(), entry.getType(), false, null);
                                                      }
                                                  }
                                                }
            block
                                                {
                                                  funcDefs.push(new FuncDef(funcHead, blocks.pop()));
                                                  scopeHandler.popScope();
                                                  scopeHandler.getScope().addFunction(funcHead.getIdent(), funcHead.getType(), funcHead.getFormParList(), true);
                                                }
;
funcHead:    type STAR? IDENT '(' formParList? ')'
                                                {
                                                  funcHeads.push(new FuncHead(types.pop(), new Ident($IDENT.text), $formParList.text != null ? formParLists.pop(): null));
                                                }
;
formParList: (
                 VOID                           { formParLists.push(VoidFormParListChild.INSTANCE); }
                 |     formParListEntry         { var entries = new ArrayList(List.of(formParListEntries.pop())); }
                    (
                          ',' formParListEntry  { entries.add(formParListEntries.pop()); }
                    )*
                                                { formParLists.push(new FormParListEntries(entries)); }
              )
;
formParListEntry: type STAR? IDENT (BRACKETS)?
                                                {
                                                    var type = toArrayTypeOptional(types.pop(), $STAR != null || $BRACKETS != null);
                                                    formParListEntries.push(new FormParListEntry(type, new Ident($IDENT.text)));
                                                }
;

type:        VOID                               { types.push(ExprType.VOID);  }     #VoidType
            | BOOL                              { types.push(ExprType.BOOL);  }     #BoolType
            | INT_LIT                           { types.push(ExprType.INT);  }      #IntType
;
block:
                                                { var entries = new ArrayList<BlockEntry>(); }
    '{'
    (
        blockEntry                              { entries.add(blockEntries.pop()); }
    )*
    '}'
                                                {
                                                    blocks.push(new Block(entries, scopeHandler.getScope()));
                                                }
;
blockEntry:
          constDef                              { blockEntries.push(constDefs.pop()); }
        | varDef                                { blockEntries.push(varDefs.pop()); }
        | stat                                  { blockEntries.push(stats.pop()); }
        ;
stat:    (  emptyStat                           { stats.push(EmptyStat.INSTANCE); }
         |                                      { scopeHandler.pushChildScope(); }
            blockStat
                                                {
                                                  stats.push(new BlockStat(blocks.pop()));
                                                  scopeHandler.popScope();
                                                }
         | exprStat                             { stats.push(new ExprStat(exprs.pop())); }
         | ifStat
         | whileStat
         | breakStat                            { stats.push(BreakStat.INSTANCE); }
         | inputStat
         | outputStat
         | deleteStat
         | returnStat
         )
;
emptyStat:   SEM;
blockStat:   block;
exprStat:    expr SEM;
ifStat:      'if' '(' expr ')' stat elseStat?
                                                {
                                                    Stat elseStat = $elseStat.text != null ? stats.pop(): null;
                                                    Stat ifStat = stats.pop();
                                                    stats.push(new IfStat(exprs.pop(), ifStat, elseStat));
                                                }
;
elseStat:    'else' stat;
whileStat:   'while' '(' expr ')' stat          { stats.push(new WhileStat(exprs.pop(), stats.pop())); }
;
breakStat:   'break' SEM;
inputStat:   'cin' '>>' IDENT SEM               { stats.add(new InputStat(new Ident($IDENT.text), scopeHandler.getScope())); }
;
outputStat:  'cout' '<<' outputStatEntry
                                                { var entries = new ArrayList(List.of(outputStatEntries.pop())); }
                    (
                    '<<' outputStatEntry        { entries.add(outputStatEntries.pop());}
                    )* SEM
                                                { stats.push(new OutputStat(entries)); }
;
outputStatEntry: expr                           {outputStatEntries.push(exprs.pop());}                             #ExprOutputStatEntry
                | STRING                        {outputStatEntries.push(new Text(formatString($STRING.text)));}    #StringOutputStatEntry
                | 'endl'                        {outputStatEntries.push(Endl.INSTANCE);}                           #EndlOutputStatEntry
;
deleteStat:  'delete' BRACKETS IDENT SEM        {stats.add(new DeleteStat(new Ident($IDENT.text),scopeHandler.getScope()));}
;
returnStat:  'return' (expr)? SEM               { stats.push(new ReturnStat($expr.text != null ? exprs.pop(): null)); }
;
expr:                                           {var entries = new ArrayList<ExprEntry>();}
        orExpr
        (
        exprEntry                               { entries.add(exprEntries.pop()); }
        )*
                                                { exprs.push(new Expr(orExprs.pop(), entries, scopeHandler.getScope())); }
;
exprEntry: exprAssign orExpr                    { exprEntries.push(new ExprEntry(orExprs.pop(), assignOperators.pop())); }
;
exprAssign:  EQUAL                              { assignOperators.push(AssignOperator.ASSIGN);}     #EqualAssign
           | ADD_ASSIGN                         { assignOperators.push(AssignOperator.ADD_ASSIGN);} #AddAssign
           | SUB_ASSIGN                         { assignOperators.push(AssignOperator.SUB_ASSIGN);} #SubAssign
           | MUL_ASSIGN                         { assignOperators.push(AssignOperator.MUL_ASSIGN);} #MulAssign
           | DIV_ASSIGN                         { assignOperators.push(AssignOperator.DIV_ASSIGN);} #DivAssign
           | MOD_ASSIGN                         { assignOperators.push(AssignOperator.MOD_ASSIGN);} #ModAssign
           ;
orExpr:     andExpr                             {var entries = new ArrayList(List.of(andExprs.pop()));}
            (
                '||' andExpr                    {entries.add(andExprs.pop());}
            )*
                                                { orExprs.push(new OrExpr(entries, scopeHandler.getScope())); }
;
andExpr:     relExpr                            { var entries = new ArrayList(List.of(relExprs.pop())); }
            (
                '&&' relExpr                    { entries.add(relExprs.pop()); }
            )*
                                                { andExprs.push(new AndExpr(entries)); }
;
relExpr:     simpleExpr                         { var entries = new ArrayList<RelExprEntry>(); }
             (
                relExprEntry                    { entries.add(relExprEntries.pop());}
             )*
                                                { relExprs.push(new RelExpr(simpleExprs.pop(), entries)); }
;
relExprEntry: relOperator simpleExpr            { relExprEntries.push(new RelExprEntry(simpleExprs.pop(), relOperators.pop())); }
;
relOperator: EQUAL_EQUAL                        { relOperators.push(RelOperator.EQUAL);}                         #EqualEqualOperator
            | NOT_EQUAL                         { relOperators.push(RelOperator.NOT_EQUAL);}                     #NotEqualOperator
            | LT                                { relOperators.push(RelOperator.LESS_THAN);}                     #LessThanOperator
            | LE                                { relOperators.push(RelOperator.LESS_THAN_EQUAL);}               #LessEqualOperator
            | GT                                { relOperators.push(RelOperator.GREATER_THAN);}                  #GreaterThanOperator
            | GE                                { relOperators.push(RelOperator.GREATER_THAN_EQUAL);}            #GreaterEqualOperator
;
simpleExpr:  (SIGN)? term                       { var entries = new ArrayList<SimpleExprEntry>(); }
             (
                simpleExprEntry                 { entries.add(simpleExprEntries.pop());}
             )*
                                                { Sign sign = null;
                                                  if ($SIGN != null) {
                                                      sign = $SIGN.text.equals("-") ? Sign.MINUS : Sign.PLUS;
                                                  }
                                                  simpleExprs.push(new SimpleExpr(sign, terms.pop(), entries));
                                                }
;
simpleExprEntry: SIGN term
                                                {
                                                  var sign = $SIGN.text.equals("-") ? Sign.MINUS : Sign.PLUS;
                                                  simpleExprEntries.push(new SimpleExprEntry(sign, terms.pop()));
                                                }
;
term:        notFact
                                                { var entries = new ArrayList<TermEntry>(); }
            (
                termEntry                       { entries.add(termEntries.pop());}
            )*
                                                { terms.push(new Term(notFacts.pop(), entries)); }
;
termEntry: termOperator notFact                 { termEntries.push(new TermEntry(notFacts.pop(), termOperators.pop())); }
;
termOperator:   STAR                            { termOperators.push(TermOperator.MUL);}         #StarOperator
               | DIV                            { termOperators.push(TermOperator.DIV);}         #DivOperator
               | MOD                            { termOperators.push(TermOperator.MOD);}         #ModOperator
;
notFact:     NOT? fact                          { notFacts.push(new NotFact($NOT != null, facts.pop())); }
;
fact:
               BOOLEAN                          { facts.add(new BoolType(Boolean.parseBoolean($BOOLEAN.text)));}             #BooleanFact
             | NULLPTR                          { facts.add(NullPtrType.INSTANCE);}                                          #NullptrFact
             | INT                              { facts.add(new IntType(Integer.parseInt($INT.text)));}                      #IntFact
             | callFactEntry                    { facts.add(actionFacts.pop());}                                             #CallFact
             | NEW type '[' expr ']'            { facts.add(new NewArrayTypeFact(types.pop(), exprs.pop()));}                #NewArrayFact
             | '(' expr ')'                     { facts.add(new ExprFact(exprs.pop()));}                                     #ExprFact
;
callFactEntry:
            preIncDec=INC_DEC?
            IDENT
            callFactEntryOperation?
            postIncDec=INC_DEC?
                                                {
                                                 IncDec preIncDec = null;
                                                 IncDec postIncDec = null;
                                                  if ($preIncDec != null) {
                                                       preIncDec = $preIncDec.text.equals("++") ? IncDec.INCREASE : IncDec.DECREASE;
                                                  }
                                                  if ($postIncDec != null) {
                                                       postIncDec = $postIncDec.text.equals("++") ? IncDec.INCREASE : IncDec.DECREASE;
                                                  }
                                                  ActionOperation actionOperation = null;
                                                  if ($callFactEntryOperation.text != null) {
                                                      actionOperation = actionOperations.pop();
                                                  }
                                                  actionFacts.push(new ActionFact(preIncDec, new Ident($IDENT.text), actionOperation, postIncDec,scopeHandler.getScope()));
                                                }
;
callFactEntryOperation:
           ( '[' expr    ']')                   { actionOperations.push(new ArrayAccessOperation(exprs.pop(), scopeHandler.getScope())); }       #ExprFactOperation
           | ( '(' (actParList)?    ')')
                                                {
                                                  var actParList = new ArrayList<Expr>();
                                                  if($actParList.text != null) {
                                                      actParList.addAll(actParLists.pop());
                                                  }

                                                  actionOperations.push(new CallOperation(actParList, scopeHandler.getScope()));
                                                }  #ActParListFactOperation
;
actParList:  expr                               { var entries = new ArrayList(List.of(exprs.pop()));}
             (
                ',' expr                        { entries.add(exprs.pop()); }
             )*
                                                { actParLists.push(entries); }
;

INC_DEC: INC | DEC;
BOOLEAN: TRUE | FALSE;
SEM: ';';
SIGN: '+' | '-';
TRUE: 'true';
FALSE: 'false';
NULLPTR: 'nullptr';
CONST : 'const' ;
NEW : 'new' ;
INT_LIT: 'int';
BOOL : 'bool' ;
EQUAL : '=' ;
STAR : '*' ;
VOID : 'void' ;
IF : 'if' ;
ELSE : 'else' ;
WHILE : 'while' ;
BREAK : 'break' ;
CIN : 'cin' ;
COUT : 'cout' ;
ENDL : 'endl' ;
DELETE : 'delete' ;
RETURN : 'return' ;
ADD_ASSIGN : '+=' ;
SUB_ASSIGN : '-=' ;
MUL_ASSIGN : '*=' ;
DIV_ASSIGN : '/=' ;
MOD_ASSIGN : '%=' ;
OR : '||' ;
AND : '&&' ;
EQUAL_EQUAL : '==' ;
NOT_EQUAL : '!=' ;
LT : '<' ;
LE : '<=' ;
GT : '>' ;
GE : '>=' ;
DIV : '/' ;
MOD : '%' ;
NOT : '!' ;
INC : '++' ;
DEC : '--' ;
BRACKETS: '[' ']';
IDENT: [a-zA-Z_][a-zA-Z_0-9]*;
INT: [0-9]+;
STRING: '"' (~[\r\n"] | '""')* '"';
WS: [ \t\n\r]+ -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
