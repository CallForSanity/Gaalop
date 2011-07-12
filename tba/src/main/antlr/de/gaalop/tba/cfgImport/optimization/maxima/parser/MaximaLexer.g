
lexer grammar MaximaLexer;

@header {
	package de.gaalop.tba.cfgImport.optimization.maxima.parser;
}


IDENTIFIER
	:	LETTER (LETTER|DIGIT)*
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
	
DECIMAL_LITERAL
	: ('0'..'9')+
	;

FLOATING_POINT_LITERAL
    :   ('0'..'9') '.' ('0'..'9')+
    ;

fragment
EXPONENT 
	: 'e' MINUS? ('0'..'9')+
	;

LBRACKET
	:	'('
	;

RBRACKET
	:	')'
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
	

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;
