lexer grammar TestbenchLexer;

options {
  language = Java;
}

@header {
  package de.gaalop.testbench;
}

DECIMAL_LITERAL : ('0'..'9')+;

FLOATING_POINT_LITERAL
    :   (PLUS | MINUS)? ('0'..'9')+ '.' ('0'..'9')*
    |   (PLUS | MINUS)? ('0'..'9')+ 
  ;

WS  : (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;} ;

LETTER :
  'a'..'z' |
  'A'..'Z';
  

PLUS : '+';
MINUS : '-';

LBRACKET : '(';
RBRACKET : ')';

WEDGE : '^';


