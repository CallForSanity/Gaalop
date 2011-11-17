
parser grammar GPCParser;

options {
	output = AST;
	backtrack = true;
	memoize = true;
	tokenVocab = GPCLexer;
}

tokens {
	FUNCTION;
	VARIABLE;
	NEGATION; // Unary Negation
}

@header {
	package de.gaalop.gpc;
	
	import java.util.List;
	import java.util.ArrayList;
}

/*
	Grammar Rules
*/
program
	: assignment_expression
	;

assignment_expression
	: additive_expression EQUALS^ additive_expression SEMICOLON!
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

argument_expression_list
  :   additive_expression ( COMMA! additive_expression )*
  ;

function_call
	: (name=IDENTIFIER LBRACKET args=argument_expression_list RBRACKET)
	-> ^(FUNCTION $name $args)
	;

unary_operator
	: MINUS
	;

primary_expression
	: variable
	| constant
	| LBRACKET! additive_expression RBRACKET!
	;
	
variable: name=IDENTIFIER -> ^(VARIABLE $name)
	;
	
constant
    :   DECIMAL_LITERAL
    |   FLOATING_POINT_LITERAL
    ;