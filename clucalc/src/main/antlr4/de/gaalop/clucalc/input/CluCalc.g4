grammar CluCalc;

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

UNROLL_LITERAL: 'unroll';

COUNT_LITERAL: 'count';

STRING_LITERAL
    :  '"' ( ~('\\'|'"') )* '"'
    ;

fragment
EXPONENT 
	: 'e' MINUS? ('0'..'9')+
	;

fragment
FLOATTYPESUFFIX
	: ('f'|'d')
	;
	
OPNS    :	'OPNS'
	;
	
IPNS	:	'IPNS'
	;
  
SLIDER_LITERAL : 'Slider'
  ;

COLOR_LITERAL :  'Color'
  ;
  
BGCOLOR_LITERAL : '_BGColor'
  ;
  
BLACK : 'Black';
BLUE : 'Blue';
CYAN : 'Cyan';
GREEN : 'Green';
MAGENTA : 'Magenta';
ORANGE : 'Orange';
RED : 'Red';
WHITE : 'White';
YELLOW : 'Yellow';


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
        |       '$'
	;

fragment
DIGIT	:	'0'..'9'
	;

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') -> skip
    ;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

// rely on the fact that they are recognized in the order in this file
// (LINE_COMMENT also matches PRAGMA)
PRAGMA
    :   '//#pragma'
    ;

LINE_COMMENT
    : '//'~'#'  ~('\n'|'\r')* '\r'? '\n' -> skip
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
        : '->'
        ;

// Parser rules

/*
  Root Rule 
*/
script  :
  statement* EOF
  ;

statement 
  : expression_statement
  | color
  | bgcolor
  | macro_definition
  | draw_mode
  ;

statement_list
  : statement*
  ;

expression_statement
  : SEMICOLON                                               #EmptyStatement
  | QUESTIONMARK val=assignment SEMICOLON                   #OutputAssignmentCaseQ
  | QUESTIONMARK val=variable SEMICOLON                     #OutputVariableCaseQ
  | COLON val=assignment SEMICOLON                          #OutputAssignmentCaseC
  | COLON val=variable SEMICOLON                            #OutputVariableCaseC
  | NOT val=assignment SEMICOLON                            #EvaluateAssignmentCase
  | val=assignment SEMICOLON                                #AssignmentCase
  ;

color 
  : (COLON COLOR_LITERAL LBRACKET args=argument_expression_list RBRACKET SEMICOLON) #COLORARGS
  | COLON name=color_name SEMICOLON                                                 #COLORNAME
  ;
  
color_name
  : (BLACK | BLUE | CYAN | GREEN | MAGENTA | ORANGE | RED | WHITE | YELLOW)
  ;
  
bgcolor
  : BGCOLOR_LITERAL EQUALS COLOR_LITERAL LBRACKET args=argument_expression_list RBRACKET SEMICOLON 
  ;

argument_expression_list
  :   arg=expression                                            #SingleArgument
  |   arg=expression COMMA nextarg=argument_expression_list     #MultipleArgument
  ;

float_literal:
  MINUS? FLOATING_POINT_LITERAL
  ;
  
float_or_dec
  : (MINUS)? DECIMAL_LITERAL
  | float_literal
  ;
  
macro_definition
  : id=IDENTIFIER EQUALS CLBRACKET body=statement_list e=return_value? CRBRACKET #MACRO
  ;
  
return_value
  : expr=expression #RETURN_ADD
  ;
  
draw_mode
  : (COLON IPNS) #IPNS
  | (COLON OPNS) #OPNS
  ;
  
/*
  Expressions
*/
assignment
  : var=variable EQUALS expr=expression       
  ;

expression
  : LBRACKET e=expression RBRACKET                        #Bracket
  | STAR operand=expression                               #Dual
  | REVERSE operand=expression                            #Reverse
  | left=expression WEDGE right=expression                #OuterProduct
  | left=expression DOT right=expression                  #InnerProduct
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

primary_expression
  : variable
  | function_argument
  | constant         
  ;

variable
  : IDENTIFIER
  ;

function_argument
  : ARGUMENT_PREFIX index=DECIMAL_LITERAL RBRACKET        #Argument
  ;

constant
  :   DECIMAL_LITERAL
  |   FLOATING_POINT_LITERAL
  ;
