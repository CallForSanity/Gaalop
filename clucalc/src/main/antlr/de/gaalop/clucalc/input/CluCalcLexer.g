
lexer grammar CluCalcLexer;

@header {
	package de.gaalop.clucalc.input;
}

/*
	Lexer Rules
*/

DECIMAL_LITERAL : ('0'..'9')+;

FLOATING_POINT_LITERAL
    :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT? FLOATTYPESUFFIX?
    |   '.' ('0'..'9')+ EXPONENT? FLOATTYPESUFFIX?
    |   ('0'..'9')+ EXPONENT FLOATTYPESUFFIX?
    |   ('0'..'9')+  FLOATTYPESUFFIX
	;

RANGE_LITERAL : 'range';

OUTPUT_LITERAL: 'output';

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
	
LOOP  : 'loop'
  ;
  
BREAK : 'break'
  ;

IDENTIFIER
	:	('::')? LETTER (LETTER|DIGIT)*
	;
	
ARGUMENT_PREFIX
  : '_P('
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

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
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
    : '//'~'#'  ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;


EQUALS
	:	'='
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
	
DOT	:	'.'
	;

	
QUESTIONMARK
	:	'?'
	;
	
COLON	
	:	':'
	;

DOUBLE_BAR 
	:	 '||'
	;
	
DOUBLE_AND 
	:	'&&'
	;
	
DOUBLE_EQUALS 
	:	'=='
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

LESS_OR_EQUAL 
	:	'<='
	;
	
GREATER_OR_EQUAL 
	:	'>='
	;

REFERENCE_OPERATOR
  @after{ 
    throw new IllegalArgumentException("The reference operator (->) is not supported. Please use standard assignments instead."); 
  }
  : '->'
  ;

