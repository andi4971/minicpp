grammar minicpp;

miniCpp:     (constDef|varDef| funcDecl|funcDef| SEM )* EOF;

constDef:    'const' type IDENT init (',' IDENT init)* SEM;
init:        '=' ( 'true' |'false'  | 'nullptr'
                 | (PLUSMINUS)? number );
varDef:      type ('*')? IDENT (init)?
             (',' ('*')? IDENT (init)? )* SEM;

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
fact:        ( 'false' | 'true' | 'nullptr' | number
             | ('++' | '--')?
               IDENT ( ( '[' expr             ']')
                  | ( '(' (actParList)?    ')')
                  )?
               ('++' | '--')?
             | 'new' type '[' expr ']'
             | '(' expr ')'
             );
actParList:  expr (',' expr)*;

number: INT | FLOAT;

/** Keywords */

IDENT: [a-zA-Z_][a-zA-Z_0-9]*;
INT: [0-9]+;
FLOAT: [0-9]+'.'[0-9]*;
STRING: '"' (~[\r\n"] | '""')* '"';

SEM: ';';
PLUSMINUS: '+' | '-';


WS: [ \t\n\r]+ -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
