
parser grammar MaximaParser;

options {
	output = AST;
	backtrack = true;
	memoize = true;
	tokenVocab = MaximaLexer;
}

tokens {
	FUNCTION;
	MV_SUBSCRIPT; // Accessing one MV component
	VARIABLE;
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
program
	: additive_expression
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
	: primary_expression
	| function_call
	;

function_call
	: (name=IDENTIFIER LBRACKET args=additive_expression RBRACKET)
	-> ^(FUNCTION $name $args)
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
	: name=IDENTIFIER UNDERLINE index=DECIMAL_LITERAL -> ^(MV_SUBSCRIPT $name $index)
	;
	
variable: name=IDENTIFIER -> ^(VARIABLE $name)
	;
	
constant
    :   DECIMAL_LITERAL
    |   FLOATING_POINT_LITERAL
    ;
