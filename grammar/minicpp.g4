grammar minicpp;

miniCpp:     (constDef|varDef| funcDecl|funcDef| SEM )* EOF;

constDef:    'const' type IDENT init (',' IDENT init)* SEM;
init:        '=' (  BOOLEAN | NULLPTR
                 | (PLUSMINUS)? INT );
varDef:      type varDefEnty
             (',' varDefEnty)* SEM;
varDefEnty: ('*')? IDENT (init)?;
funcDecl:    funcHead SEM;
funcDef:     funcHead block;
funcHead:    type ('*')? IDENT '(' formParList? ')';
formParList: ('void'
              |     type ('*')? IDENT ('[' ']')?
               (',' type ('*')? IDENT ('[' ']')?)*
              );
type:        'void' | 'bool' | 'int';
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
outputStat:  'cout' '<<' ( expr | STRING | 'endl')
                    ('<<' ( expr | STRING | 'endl') )* SEM;
deleteStat:  'delete' '[' ']' IDENT SEM;
returnStat:  'return' (expr)? SEM;
expr:        orExpr ( ('=' | '+=' | '-=' | '*=' | '/=' | '%=') orExpr )*;
orExpr:      andExpr ( '||' andExpr )*;
andExpr:     relExpr ( '&&' relExpr )*;
relExpr:     simpleExpr
             ( ('==' | '!=' | '<' | '<=' | '>' | '>=') simpleExpr )*;
simpleExpr:  (PLUSMINUS)?
             term ( (PLUSMINUS) term )*;
term:        notFact ( ('*' | '/' | '%') notFact )*;
notFact:     ('!')? fact;
fact:        ( BOOLEAN | 'nullptr' | INT
             | ('++' | '--')?
               IDENT ( ( '[' expr             ']')
                  | ( '(' (actParList)?    ')')
                  )?
               ('++' | '--')?
             | 'new' type '[' expr ']'
             | '(' expr ')'
             );
actParList:  expr (',' expr)*;

BOOLEAN: TRUE | FALSE;
/** Keywords */



SEM: ';';
PLUSMINUS: '+' | '-';
TRUE: 'true';
FALSE: 'false';
NULLPTR: 'nullptr';

/*CONST : 'const' ;
EQUAL : '=' ;
MUL : '*' ;
VOID : 'void' ;
IF : 'if' ;
ELSE : 'else' ;
WHILE : 'while' ;
BREAK : 'break' ;
CIN : 'cin' ;
T__1 : '>>' ;
COUT : 'cout' ;
T__3 : '<<' ;
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
BANG : '!' ;
INC : '++' ;
DEC : '--' ;
NEW : 'new' ;*/
IDENT: [a-zA-Z_][a-zA-Z_0-9]*;
INT: [0-9]+;
STRING: '"' (~[\r\n"] | '""')* '"';
WS: [ \t\n\r]+ -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
