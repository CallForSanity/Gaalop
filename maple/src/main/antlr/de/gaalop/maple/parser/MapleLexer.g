
lexer grammar MapleLexer;

@header {
	package de.gaalop.maple.parser;
}

// Special keywords:
GAALOPARRAY
	:	'gaaloparray'
	;

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
    :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT?
    |   '.' ('0'..'9')+ EXPONENT?
    |   ('0'..'9')+ EXPONENT
    ;

fragment
EXPONENT 
	: 'e' MINUS? ('0'..'9')+
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

ASSIGNMENT
	:	':=';
	

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
	

SEMICOLON
	:	';'
	;

WEDGE	:	'^'
	;

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;
