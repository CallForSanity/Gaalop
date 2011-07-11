
parser grammar MaximaParser;

options {
	output = AST;
	backtrack = true;
	memoize = true;
	tokenVocab = MaximaLexer;
}

tokens {
	DECLAREARRAY;
	COEFFICIENT;
	FUNCTION;
	MV_SUBSCRIPT; // Accessing one MV component
	VARIABLE;
	ASSIGN;
	ASSIGNBLADE; // Assigns a blade to a MV component
	NEGATION; // Unary Negation

}

@header {
	package de.gaalop.tba.cfgImport.optimization.maxima.parser;
	
	import java.util.List;
	import java.util.ArrayList;
}

/*
	Grammar Rules
*/
program	: 
	statement+
	;

statement
	: ( declareArray | assignCoefficient | assignment ) SEMICOLON!
	; 

// Declare a new multivector (we know how many coefficients)
declareArray 
	: (GAALOPARRAY LBRACKET var=IDENTIFIER RBRACKET) -> ^(DECLAREARRAY $var)
	;

assignCoefficient
	: (mvName=IDENTIFIER LSBRACKET bladeIndex=DECIMAL_LITERAL RSBRACKET ASSIGNMENT value=additive_expression) -> ^(COEFFICIENT $mvName $bladeIndex $value)
	;
	
assignment
  : var=IDENTIFIER ASSIGNMENT value=additive_expression -> ^(ASSIGN $var $value)
  ;

coefficientExpression
	: coefficientBlade ( PLUS! coefficientBlade )*
	;
	
coefficientBlade
	:  mvName=IDENTIFIER LSBRACKET index=DECIMAL_LITERAL RSBRACKET STAR assignedBlade=blade -> ^(ASSIGNBLADE $index $assignedBlade)
	;
	
blade	: primary_expression
	;

additive_expression
	: multiplicative_expression ( (PLUS^ | MINUS^) multiplicative_expression )*
	;

multiplicative_expression
	: outer_product_expression ( (STAR^ | SLASH^) outer_product_expression )*
  | negation
	;

outer_product_expression
	: modulo_expression ( WEDGE^ modulo_expression )*
	;
	
modulo_expression
	: unary_expression ( MODULO^ unary_expression )*
	;

negation
	: (unary_operator value=multiplicative_expression) -> ^(NEGATION $value)
;

//negation must have lower priority than wedge
unary_expression
	: postfix_expression
	;
	
postfix_expression
	: primary_expression
	| function_call
	;

function_call
	: (name=IDENTIFIER LBRACKET args=argument_expression_list RBRACKET)
	-> ^(FUNCTION $name $args)
	;
	
argument_expression_list
	:   additive_expression ( COMMA! additive_expression )*
	;

unary_operator
	: MINUS
	;

primary_expression
	: mv_coefficient
	| variable
	| constant
	| LBRACKET! additive_expression RBRACKET!
	;

mv_coefficient
	: name=IDENTIFIER LSBRACKET index=DECIMAL_LITERAL RSBRACKET -> ^(MV_SUBSCRIPT $name $index)
	;
	
variable: name=IDENTIFIER -> ^(VARIABLE $name)
	;
	
constant
    :   DECIMAL_LITERAL
    |   FLOATING_POINT_LITERAL
    ;
