
lexer grammar GaaletLexer;

@header {
	package de.gaalop.gaalet.antlr;
}



/*
	Lexer Rules
*/

DECIMAL_LITERAL : ('0'..'9')+;

fragment 
HEX_PREFIX : '0x';

HEX : HEX_PREFIX(DIGIT | ('a'..'f'))+ ; 

RANGE_LITERAL : 'range';

OUTPUT_LITERAL: 'output';

UNROLL_LITERAL: 'unroll';

COUNT_LITERAL: 'count';

IGNORE_LITERAL: 'ignore';


LOOP  : 'loop'
  ;
  
BREAK : 'break'
  ;

GEALG_MV
  : 'gaalet::cm::mv'
  ;
  
  
GEALG_TYPE
  : '::type'
  ;


DOUBLE
  : 'double'
  ;

FLOAT 
  : 'float'
  ;
  
INTEGER
  : 'int'
  ;

UNSIGNED
  : 'unsigned'
  ;
  
SIGNED
  : 'signed'
  ;
  
AUTO
  : 'auto'
  ;

EVAL
  : 'eval'
  ; 
  
INVERSE
  : 'gaalet::inverse'
  ;

fragment
EXPONENT 
	: 'e' MINUS? ('0'..'9')+
	;

fragment
FLOATTYPESUFFIX
	: ('f'|'d')
	;
	
OPNS	:	'OPNS'
	;
	
IPNS	:	'IPNS'
	;
	
IF		:	'if'
	;
	
ELSE	:	'else'
	;
	
	
//to make IDENTIFIER resistent to spaces, define it as parser rule. Maybe that will work
IDENTIFIER
  : 
  IDENTIFIER_RECURSIVE
  ;


fragment
IDENTIFIER_RECURSIVE
  :   (LETTER) (LETTER|DIGIT|(DOT IDENTIFIER_RECURSIVE)
                  |(DOUBLE_COLON IDENTIFIER_RECURSIVE)
                  |(ARROW_RIGHT IDENTIFIER_RECURSIVE))*
      (LSBRACKET IDENTIFIER_RECURSIVE RSBRACKET)*
  ;
  
fragment
IDENTIFIER_TYPE_CAST
  : LBRACKET (DOUBLE | FLOAT | INTEGER) RBRACKET
  ; // we will probably only need double
    


FLOATING_POINT_LITERAL
    :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT? FLOATTYPESUFFIX?
    |   '.' ('0'..'9')+ EXPONENT? FLOATTYPESUFFIX?
    |   ('0'..'9')+ EXPONENT FLOATTYPESUFFIX?
    |   ('0'..'9')+  FLOATTYPESUFFIX
  ;

fragment
LETTER
	:	'A'..'Z'
	|	'a'..'z'
	|	'_'
	;

fragment
DIGIT	:	'0'..'9'
	;  
	
WS  : SPACE {$channel=HIDDEN;}
    ;
   
fragment
SPACE
  :  (' '|'\r'|'\t'|'\u000C'|'\n')
  ;


COMMA
	:	','
	;

PLUS
	:	'+'
	;

MINUS
	:	'-'
	;


STAR
	:	'*'
	;

SLASH
	:	'/'
	;

MODULO
	:	'%'
	;

LSBRACKET
	:	'['
	;

RSBRACKET
	:	']'
	;

LBRACKET
	:	'('
	;

RBRACKET
	:	')'
	;

CLBRACKET
	:	'{'
	;

CRBRACKET
	:	'}'
	;

REVERSE
	:	'~'
	;

NOT
	:	'!'
	;

DOUBLE_NOT
	:	'!!'
	;

SEMICOLON
	:	';'
	;

WEDGE	:	'^'
	;
	
fragment	
DOT	:	'.'
	;

	
QUESTIONMARK
	:	'?'
	;
	
COLON	
	:	':'
	;

fragment
DOUBLE_COLON
  : '::'
  ;

DOUBLE_BAR 
	:	 '||'
	;
	
DOUBLE_AND 
	:	'&&'
	;
	
SINGLE_AND
  : '&'
  ;
	
DOUBLE_EQUALS 
	:	'=='
	;
	
EQUALS
  : '='
  ;	
	
UNEQUAL 
	:	'!='
	;

LESS 
	:	'<'
	;

GREATER 
	:	 '>'
	;
	
fragment
ARROW_RIGHT
  : '->'
  ;
LESS_OR_EQUAL 
	:	'<='
	;
	
GREATER_OR_EQUAL 
	:	'>='
	;
	
SET_OUTPUT
  : '//?'
  ;
  

COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

// rely on the fact that they are recognized in the order in this file
// (LINE_COMMENT also matches PRAGMA)
PRAGMA
    :   '//#pragma'
    ;

LINE_COMMENT
    : '//'~('#'|'?')  ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;