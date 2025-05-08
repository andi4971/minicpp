grammar minicpp;

miniCpp:     (miniCppEntry)* EOF;
miniCppEntry:     constDef
                | varDef
                | funcDecl
                | funcDef
                | SEM
                ;
constDef:    CONST type constDefEntry (COMMA constDefEntry)* SEM;
constDefEntry: IDENT init;
init:        EQUAL  initOption;


initOption:    BOOLEAN      #BooleanInit
             | NULLPTR      #NullptrInit
             | (SIGN)? INT  #IntInit
             ;

varDef:      type varDefEntry
             (COMMA varDefEntry)* SEM;
varDefEntry: STAR? IDENT (init)?;
funcDecl:    funcHead SEM;
funcDef:     funcHead block;
funcHead:    type STAR? IDENT LPAREN formParList? RPAREN;
formParList: (VOID
              |     formParListEntry
               (COMMA formParListEntry)*
              );
formParListEntry: type STAR? IDENT (BRACKETS)?;

type:        VOID #VoidType
            | BOOL #BoolType
            | INT_LIT #IntType
            ;
block:       LBRACE (blockEntry)* RBRACE;
blockEntry: constDef|varDef|stat;
stat:        ( emptyStat  | breakStat
             | blockStat  | exprStat
             | ifStat     | whileStat
             | inputStat  | outputStat
             | deleteStat | returnStat
             );
emptyStat:   SEM;
blockStat:   block;
exprStat:    expr SEM;
ifStat:      'if' LPAREN expr RPAREN stat elseStat?;
elseStat:    'else' stat;
whileStat:   'while' LPAREN expr RPAREN stat;
breakStat:   'break' SEM;
inputStat:   'cin' T__1 IDENT SEM;
outputStat:  'cout' T__3 outputStatEntry
                    (T__3 outputStatEntry )* SEM;
outputStatEntry: expr    #ExprOutputStatEntry
                | STRING #StringOutputStatEntry
                | 'endl' #EndlOutputStatEntry
                ;
deleteStat:  'delete' BRACKETS IDENT SEM;
returnStat:  'return' (expr)? SEM;
expr:        orExpr (exprEntry)*;
exprEntry: exprAssign orExpr;
exprAssign:  EQUAL      #EqualAssign
           | ADD_ASSIGN #AddAssign
           | SUB_ASSIGN #SubAssign
           | MUL_ASSIGN #MulAssign
           | DIV_ASSIGN #DivAssign
           | MOD_ASSIGN #ModAssign
           ;
orExpr:      andExpr ( '||' andExpr )*;
andExpr:     relExpr ( '&&' relExpr )*;
relExpr:     simpleExpr
             ( relExprEntry )*;
relExprEntry: relOperator simpleExpr;
relOperator: EQUAL_EQUAL #EqualEqualOperator
            | NOT_EQUAL  #NotEqualOperator
            | LT         #LessThanOperator
            | LE         #LessEqualOperator
            | GT         #GreaterThanOperator
            | GE         #GreaterEqualOperator
            ;
simpleExpr:  (SIGN)?
             term ( simpleExprEntry )*;
simpleExprEntry: SIGN term;
term:        notFact (termEntry)*;
termEntry: termOperator notFact;
termOperator:   STAR #StarOperator
               | DIV #DivOperator
               | MOD #ModOperator
              ;
notFact:     NOT? fact;
fact:
               BOOLEAN #BooleanFact
             | NULLPTR #NullptrFact
             | INT     #IntFact
             | callFactEntry         #CallFact
             | NEW type LBRACK expr RBRACK #NewArrayFact
             | LPAREN expr RPAREN          #ExprFact
             ;
callFactEntry:
            preIncDec=INC_DEC?
              IDENT
              callFactEntryOperation?
              postIncDec=INC_DEC?
              ;
callFactEntryOperation:
                   ( LBRACK expr    RBRACK)          #ExprFactOperation
                 | ( LPAREN (actParList)?    RPAREN) #ActParListFactOperation
                 ;
actParList:  expr (COMMA expr)*;

COMMA : ',' ;
LPAREN : '(' ;
RPAREN : ')' ;
LBRACE : '{' ;
RBRACE : '}' ;
T__1 : '>>' ;
T__3 : '<<' ;
LBRACK : '[' ;
RBRACK : ']' ;
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
