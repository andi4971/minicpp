grammar minicpp;

miniCpp:     (miniCppEntry)* EOF;
miniCppEntry:    constDef     /*#ConstDefOption*/
                | varDef      /*#VarDefOption*/
                | funcDecl    /*#FuncDeclOption*/
                | funcDef     /*#FuncDefOption*/
                | emptyStat         /*#SemOption;*/
                ;
constDef:    CONST type IDENT init (',' IDENT init)* SEM;
init:        '='  initOption;
initOption:    BOOLEAN          /*#BooleanInit*/
             | NULLPTR          /*#NullptrInit*/
             | (PLUSMINUS)? INT /*#IntInit*/
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
block:       '{' (constDef|varDef|stat)* '}';
stat:        ( emptyStat  | blockStat  | exprStat
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
deleteStat:  'delete' '[' ']' IDENT SEM;
returnStat:  'return' (expr)? SEM;
expr:        orExpr (
                ( EQUAL
                | ADD_ASSIGN
                | SUB_ASSIGN
                | MUL_ASSIGN
                | DIV_ASSIGN
                | MOD_ASSIGN
                ) orExpr )*;
orExpr:      andExpr ( '||' andExpr )*;
andExpr:     relExpr ( '&&' relExpr )*;
relExpr:     simpleExpr
             ( ( EQUAL_EQUAL
                | NOT_EQUAL
                | LT
                | LE
                | GT
                | GE
                )
                simpleExpr )*;
simpleExpr:  (PLUSMINUS)?
             term ( (PLUSMINUS) term )*;
term:        notFact (termOperator notFact )*;
termOperator: (STAR | DIV | MOD);
notFact:     NOT? fact;
fact:        ( BOOLEAN | NULLPTR | INT
             | INC_DEC?
               IDENT ( ( '[' expr    ']')
                  | ( '(' (actParList)?    ')')
                  )?
               INC_DEC?
             | NEW type '[' expr ']'
             | '(' expr ')'
             );
actParList:  expr (',' expr)*;

INC_DEC: INC | DEC;
BOOLEAN: TRUE | FALSE;
SEM: ';';
PLUSMINUS: '+' | '-';
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
