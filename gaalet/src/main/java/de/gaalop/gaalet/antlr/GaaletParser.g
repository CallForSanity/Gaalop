parser grammar GaaletParser;

options {
  output=AST;
  backtrack=true;
  memoize=true;
  tokenVocab=GaaletLexer;
}

tokens {
  FUNCTION;
  PROCEDURE;
  NEGATION;
  LOGICAL_NEGATION;
  INVERSE;
  DUAL;
  BLOCK;
  ELSEIF;
  DEFINE_V;  // just the definition of a variable like: "int" or "auto"
  DEFINE_ASSIGNMENT; // we have a defintion and then an assignment following
  DEFINE_MV;
  DEFINE_MV_AND_ASSIGN;
  DEFINE_GEALG;
  BLADE;
  BLADE_ASSIGN;
  
  MACRO;
  ARGUMENT;
  RETURN;
  
  ARG_LIST_SEP;

}

@header {
  package de.gaalop.gaalet.antlr;
}

@members {
    private List<String> errors = new ArrayList<String>();
    public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
        errors.add(hdr + " " + msg);
    }
    public List<String> getErrors() {
        return errors;
    }
}

/*
  Root Rule 
*/
script  :
  statement* EOF! 
  ;

float_literal:
  MINUS? FLOATING_POINT_LITERAL
  ;

/*
  Pragma
*/
pragma
  : PRAGMA RANGE_LITERAL float_literal LESS_OR_EQUAL IDENTIFIER LESS_OR_EQUAL float_literal
  | PRAGMA OUTPUT_LITERAL IDENTIFIER
  | PRAGMA IGNORE_LITERAL IDENTIFIER
  ;

/*
  Expressions
*/
expression
  :
    type lvalue EQUALS expression  -> ^(DEFINE_ASSIGNMENT lvalue expression) 
  | type lvalue -> ^(DEFINE_V lvalue)
  | lvalue EQUALS expression   -> ^(EQUALS lvalue expression) 
  | logical_or_expression   
  | eval 
  ;

argument_expression_list
  :   ((expression  COMMA!)* expression)
  ;
  
lvalue
  : unary_expression
  ;

logical_or_expression
  : logical_and_expression (DOUBLE_BAR^ logical_and_expression)*
  ;

logical_and_expression
  : equality_expression (DOUBLE_AND^ equality_expression)*
  ;

equality_expression
  : relational_expression ((DOUBLE_EQUALS^ | UNEQUAL^) relational_expression)*
  ;

relational_expression
  : additive_expression ((LESS^ | GREATER^ | LESS_OR_EQUAL^ | GREATER_OR_EQUAL^) additive_expression)*
  ;

additive_expression
  : multiplicative_expression ( (PLUS^ | MINUS^) multiplicative_expression )*
  ;

multiplicative_expression
  : outer_product_expression ( (STAR^ | SLASH^) outer_product_expression )*
  ;

outer_product_expression
  : inner_product_expression ( WEDGE^ inner_product_expression )*
  ;

inner_product_expression
  : unary_expression ( SINGLE_AND^ unary_expression )*
  ;
 
unary_expression
  : postfix_expression
  | MINUS operand=unary_expression -> ^(NEGATION $operand)
  | REVERSE operand=unary_expression -> ^(REVERSE $operand)
  | INVERSE LBRACKET operand=unary_expression RBRACKET-> ^(INVERSE $operand)
  | NOT operand=unary_expression -> ^(LOGICAL_NEGATION $operand)
  | name=IDENTIFIER LSBRACKET blade=DECIMAL_LITERAL RSBRACKET -> ^(BLADE $name $blade)
  ;
    
  
postfix_expression
  : function_call
  | primary_expression
  ;

function_call
  : (name=IDENTIFIER LBRACKET (args=argument_expression_list) RBRACKET)
  -> ^(FUNCTION $name $args)
  | (name=IDENTIFIER LBRACKET  RBRACKET)
  -> ^(FUNCTION $name)
  ;
  
eval
  : EVAL! LBRACKET! expression RBRACKET!
  ;
  
primary_expression
  : IDENTIFIER
  | constant
  | function_argument   
  | LBRACKET! expression RBRACKET!
  
  ;
  
constant
    :   DECIMAL_LITERAL
    |   FLOATING_POINT_LITERAL
    ;

/*
  Statements
*/
statement_list
  : statement+
  ;
//  catch [RecognitionException ex] {
//    throw new RecognitionException("test"); // TODO: throw a human-readable error message
//  }

statement 
  :SEMICOLON!
  | block
  | expression SEMICOLON!
  | define_gealg_name_and_exp    
  | if_statement
  |set_output
  | macro_definition
  | loop
  | BREAK
  | pragma   
  ;

block
  : CLBRACKET statement* CRBRACKET -> ^(BLOCK statement*)
  ;
 
  
fragment float_or_dec
  : (MINUS)? DECIMAL_LITERAL
  | float_literal
  ;
  
macro_definition
  : id=IDENTIFIER EQUALS CLBRACKET statement* e=return_value? CRBRACKET -> ^(MACRO $id statement* $e?)
  ;
  
return_value
  : add=additive_expression -> ^(RETURN $add)
  | or=logical_or_expression -> ^(RETURN $or)
  ;  

loop
  : (PRAGMA UNROLL_LITERAL number=DECIMAL_LITERAL
    | PRAGMA COUNT_LITERAL varname=IDENTIFIER)? LOOP stmt=statement -> ^(LOOP $stmt $number? $varname?)
  ;  
  
if_statement 
  : IF LBRACKET condition=logical_or_expression RBRACKET     // condition
    then_part=statement                                      // then-part
    (else_part)?                                             //  optional else-part
    -> ^(IF $condition $then_part else_part?)                      
  ;
  
else_part
  : ELSE block -> ^(ELSE block)
  | ELSE stmt=if_statement -> ^(ELSEIF $stmt)
  ;
  
  
//// new C C++ specifics
hex_literal
  : HEX
  ; 
  
function_argument
  : ARGUMENT_PREFIX index=DECIMAL_LITERAL RBRACKET -> ^(ARGUMENT $index)
  ;
  
gaalet_arguments
  : (DECIMAL_LITERAL (COMMA! DECIMAL_LITERAL)*)
  | (hex_literal (COMMA! hex_literal)*)
  ;  

  
define_gealg_name_and_exp
  : GEALG_MV 
      LESS   blades=gaalet_arguments  GREATER 
     GEALG_TYPE name=IDENTIFIER    
    (   EQUALS (( (value=expression SEMICOLON)
                      -> ^(DEFINE_MV_AND_ASSIGN $name  $blades $value))
                 | ((CLBRACKET values=argument_expression_list CRBRACKET SEMICOLON)
                      -> ^(DEFINE_GEALG $name $blades ARG_LIST_SEP $values)) 
                )      
      | SEMICOLON 
          -> ^(DEFINE_MV $name $blades )  
    )
  ;
   
type
  : ((SIGNED | UNSIGNED) (FLOAT | INTEGER | DOUBLE) )
  | (FLOAT | INTEGER | DOUBLE)
  | AUTO
  ;
  
set_output
  : SET_OUTPUT value=lvalue  -> ^(SET_OUTPUT $value)
  ;