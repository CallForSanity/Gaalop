grammar GPC;

COMMENT
        :   '/*' .*? '*/' -> skip
        ;

LINE_COMMENT
        : '//'~'#'  ~('\n'|'\r')* '\r'? '\n' -> skip
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
        :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT? FLOATTYPESUFFIX?
        |   '.' ('0'..'9')+ EXPONENT? FLOATTYPESUFFIX?
        |   ('0'..'9')+ EXPONENT FLOATTYPESUFFIX?
        |   ('0'..'9')+  FLOATTYPESUFFIX
	;

fragment
FLOATTYPESUFFIX
	: ('f'|'d')
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
	

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') -> skip
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

program
        : EOF
	| assignment_expression
	;

assignment_expression
	: (CONST_FLOATMV | CONST_TEMPMV)? var=variable EQUALS expr=expression SEMICOLON
	;

expression
        : LBRACKET e=expression RBRACKET                        #Bracket
        | left=expression WEDGE right=expression                #OuterProduct
        | left=expression SLASH right=expression                #Division
        | left=expression STAR right=expression                 #Multiplication
        | left=expression MINUS right=expression                #Subtraction
        | left=expression PLUS right=expression                 #Addition
        | name=IDENTIFIER LBRACKET args=argument_expression_list? RBRACKET      #Function
        | MINUS LBRACKET operand=expression RBRACKET            #NegationBracket
        | MINUS name=IDENTIFIER LBRACKET args=argument_expression_list? RBRACKET      #NegationFunction
        | MINUS operand=primary_expression                      #Negation
        | primary_expression                                    #Primary
        ;

argument_expression_list
  :   arg=expression                                            #SingleArgument
  |   arg=expression COMMA nextarg=argument_expression_list     #MultipleArgument
  ;

primary_expression
	: variable
	| constant
	;
	
variable
        : IDENTIFIER
        ;
	
constant
        : DECIMAL_LITERAL
        | FLOATING_POINT_LITERAL
        ;