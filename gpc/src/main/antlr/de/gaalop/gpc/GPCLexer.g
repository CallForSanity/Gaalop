
lexer grammar GPCLexer;

@header {
	package de.gaalop.gpc;
}

COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

LINE_COMMENT
    : '//'~'#'  ~('\n'|'\r')* '\r'? ('\n'|EOF) {$channel=HIDDEN;}
    ;

CONST_FLOATMV
        : 'const' WS+ 'floatmv'
        ;

CONST_TEMPMV
        : 'const' WS+ 'tempmv'
        ;

IDENTIFIER
	:	LETTER (LETTER|DIGIT|'.')*
	;

fragment
LETTER
	:	'A'..'Z'
	|	'a'..'z'
        |       '_'
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
	: 'E' MINUS? ('0'..'9')+
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

WEDGE
        :	'^'
	;

UNDERLINE
        :	'\\$'
	;
	

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;

COMMA
        :       ','
        ;

SEMICOLON
        :       ';'
        ;

EQUALS
        :       '='
        ;