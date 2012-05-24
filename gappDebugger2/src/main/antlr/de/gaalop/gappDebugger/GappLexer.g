
lexer grammar GappLexer;

@header {
	package de.gaalop.gappDebugger;
}

/*
	Lexer Rules
*/

ASSIGNINPUTSVECTOR_LITERAL
    : 'assignInputsVector'
    ;


RESETMV_LITERAL
    : 'resetMv'
    ;


SETMV_LITERAL
    : 'setMv'
    ;

SETVECTOR_LITERAL
    : 'setVector'
    ;

DOTVECTORS_LITERAL
    : 'dotVectors'
    ;

ASSIGNMV_LITERAL
    : 'assignMv'
    ;

CALCULATEMVCOEFF_LITERAL
    : 'calculateMvCoeff'
    ;

CALCULATEMV_LITERAL
    : 'calculateMv'
    ;

CALC_OP_DIVISION_LITERAL
    : 'DIVISION'
    ;

CALC_OP_EXPONENTIATION_LITERAL
    : 'EXPONENTIATION'
    ;

CALC_OP_INVERT_LITERAL
    : 'INVERT'
    ;

CALC_OP_ABS_LITERAL
    : 'ABS'
    ;

CALC_OP_ACOS_LITERAL
    : 'ACOS'
    ;

CALC_OP_ASIN_LITERAL
    : 'ASIN'
    ;

CALC_OP_ATAN_LITERAL
    : 'ATAN'
    ;

CALC_OP_CEIL_LITERAL
    : 'CEIL'
    ;

CALC_OP_COS_LITERAL
    : 'COS'
    ;

CALC_OP_EXP_LITERAL
    : 'EXP'
    ;

CALC_OP_FACT_LITERAL
    : 'FACT'
    ;

CALC_OP_FLOOR_LITERAL
    : 'FLOOR'
    ;

CALC_OP_LOG_LITERAL
    : 'LOG'
    ;

CALC_OP_SIN_LITERAL
    : 'SIN'
    ;

CALC_OP_SQRT_LITERAL
    : 'SQRT'
    ;

CALC_OP_TAN_LITERAL
    : 'TAN'
    ;


DECIMAL_LITERAL : ('0'..'9')+;

FLOATING_POINT_LITERAL
    :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT?
    |   '.' ('0'..'9')+ EXPONENT? 
    |   ('0'..'9')+ EXPONENT 
    |   ('0'..'9')+  
	;



fragment
EXPONENT 
	: 'e' MINUS? ('0'..'9')+
	;

IDENTIFIER
	:	('::')? LETTER (LETTER|DIGIT)*
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

LINE_COMMENT
    : '//'~'#'  ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;






EQUALS
	:	'='
	;


COMMA
	:	','
	;

MINUS
	:	'-'
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

SEMICOLON
	:	';'
	;

LESS 	
	:	'<'
	;

GREATER 
	:	 '>'
	;