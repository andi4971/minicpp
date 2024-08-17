grammar minicpp;

miniCpp:     (miniCppEntry)* EOF;
miniCppEntry:    constDef    
                | varDef
                | funcDecl
                | funcDef
                | SEM
                ;
constDef:    CONST type constDefEntry (',' constDefEntry)* SEM;
constDefEntry: IDENT init;
init:        '='  initOption;
initOption:    BOOLEAN          #BooleanInit
             | NULLPTR          #NullptrInit
             | (SIGN)? INT #IntInit
             ;

varDef:      type varDefEntry
             (',' varDefEntry)* SEM;
varDefEntry: STAR? IDENT (init)?;
funcDecl:    funcHead SEM;
funcDef:     funcHead block;
funcHead:    type STAR? IDENT '(' formParList? ')';
formParList: (VOID
              |     formParListEntry
               (',' formParListEntry)*
              );
formParListEntry: type STAR? IDENT (BRACKETS)?;

type:        VOID | BOOL | INT_LIT;
block:       '{' (blockEntry)* '}';
blockEntry: constDef|varDef|stat;
stat:        ( emptyStat
             | blockStat  | exprStat
             | ifStat     | whileStat  | breakStat
             | inputStat  | outputStat
             | deleteStat | returnStat
             );
emptyStat:   SEM;
blockStat:   block;
exprStat:    expr SEM;
ifStat:      'if' '(' expr ')' stat ('else' stat)?;
whileStat:   'while' '(' expr ')' stat;
breakStat:   'break' SEM;
inputStat:   'cin' '>>' IDENT SEM;
outputStat:  'cout' '<<' outputStatEntry
                    ('<<' outputStatEntry )* SEM;
outputStatEntry: expr | STRING | 'endl';
deleteStat:  'delete' BRACKETS IDENT SEM;
returnStat:  'return' (expr)? SEM;
expr:        orExpr (exprEntry)*;
exprEntry: exprAssign orExpr;
exprAssign: EQUAL #EqualAssign
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
            | NOT_EQUAL #NotEqualOperator
            | LT #LessThanOperator
            | LE #LessEqualOperator
            | GT #GreaterThanOperator
            | GE #GreaterEqualOperator
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
             | INT #IntFact
             | callFactEntry #CallFact
             | NEW type '[' expr ']' #NewArrayFact
             | '(' expr ')' #ExprFact
             ;
callFactEntry:
            INC_DEC?
              IDENT ( ( '[' expr    ']')
                 | ( '(' (actParList)?    ')')
                 )?
              INC_DEC?
              ;
actParList:  expr (',' expr)*;

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
