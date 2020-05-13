grammar Maxima;

IDENTIFIER
	:	LETTER (LETTER|DIGIT)*
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
        | 'E' PLUS ('0'..'9')+
	;

LBRACKET
	:	'('
	;

RBRACKET
	:	')'
	;

MODULO
	:	'%'
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
	

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') -> skip
    ;



// Parser rules

expression
        : LBRACKET ex=expression RBRACKET                          #Bracket
        | left=expression WEDGE right=expression                #Exponentiation
        | left=expression SLASH right=expression                #Division
        | left=expression STAR right=expression                 #Multiplication
        | left=expression MINUS right=expression                #Subtraction
        | left=expression PLUS right=expression                 #Addition
        | MINUS LBRACKET operand=expression RBRACKET            #NegationBracket
        | MINUS operand=expression                              #Negation
        | name=IDENTIFIER LBRACKET arg=expression RBRACKET      #Function
        | primary_expression                                    #Primary
        ;



primary_expression
	: mv_coefficient
	| variable
	| constant
	;

mv_coefficient
	: name=IDENTIFIER UNDERLINE index=DECIMAL_LITERAL
	;
	
variable
        : name=IDENTIFIER
	;
	
constant
    :   DECIMAL_LITERAL
    |   FLOATING_POINT_LITERAL 
    |   '%pi'
    |   '%i'
    ;