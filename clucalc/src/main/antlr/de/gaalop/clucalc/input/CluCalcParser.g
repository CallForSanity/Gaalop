parser grammar CluCalcParser;

options {
  output=AST;
  backtrack=true;
  memoize=true;
  tokenVocab=CluCalcLexer;
}

tokens {
  FUNCTION;
  NEGATION;
  DUAL;
  BLOCK;
  ELSEIF;
  MACRO;
  ARGUMENT;
  RETURN;
  SLIDER;
  STRING;
  COLOR;
  BGCOLOR;
}

@header {
  package de.gaalop.clucalc.input;
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
  | PRAGMA OUTPUT_LITERAL IDENTIFIER blade+ SEMICOLON
  | PRAGMA ONLY_EVAL_LITERAL IDENTIFIER+ SEMICOLON
  | PRAGMA IGNORE_LITERAL IDENTIFIER
  ;

blade
  : FLOATING_POINT_LITERAL
  | IDENTIFIER (WEDGE IDENTIFIER)*
  ;

/*
  Expressions
*/
expression
  : lvalue EQUALS expression -> ^(EQUALS lvalue expression)
  | logical_or_expression
  ;

argument_expression_list
  :   expression ( COMMA! expression )*
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
  : modulo_expression ( DOT^ modulo_expression )*
  ;

modulo_expression
  : unary_expression ( MODULO^ unary_expression )*
  ;

unary_expression
  : postfix_expression
  | STAR operand=unary_expression -> ^(DUAL $operand)
  | MINUS operand=unary_expression -> ^(NEGATION $operand)
  | REVERSE operand=unary_expression -> ^(REVERSE $operand)
  | DOUBLE_NOT operand=unary_expression -> ^(DOUBLE_NOT $operand)
  ;
  
postfix_expression
  : primary_expression
  | function_call
  ;

function_call
  : (name=IDENTIFIER LBRACKET args=argument_expression_list? RBRACKET)
  -> ^(FUNCTION $name $args?)
  ;

primary_expression
  : IDENTIFIER
  | function_argument 
  | constant
  | LBRACKET! expression RBRACKET!
  ;
constant
    :   DECIMAL_LITERAL
    |   FLOATING_POINT_LITERAL
    ;
    
color 
  : (COLON COLOR_LITERAL LBRACKET args=argument_expression_list RBRACKET SEMICOLON) -> ^(COLOR $args)
  | COLON name=color_name SEMICOLON -> ^(COLOR $name)
  ;
  
color_name
  : (BLACK | BLUE | CYAN | GREEN | MAGENTA | ORANGE | RED | WHITE | YELLOW)
  ;
  
bgcolor
  : BGCOLOR_LITERAL EQUALS COLOR_LITERAL LBRACKET args=argument_expression_list RBRACKET SEMICOLON -> ^(BGCOLOR $args)
  ;
    
function_argument
  : ARGUMENT_PREFIX index=DECIMAL_LITERAL RBRACKET -> ^(ARGUMENT $index)
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
  : expression_statement
  | slider
  | color
  | bgcolor
  | macro_definition
  | draw_mode
  | block
  | if_statement
  | loop
  | BREAK
  | pragma 
  ;
  
fragment slider
  : IDENTIFIER EQUALS SLIDER_LITERAL LBRACKET slider_args RBRACKET SEMICOLON -> ^(SLIDER IDENTIFIER slider_args)
  ;
    
fragment slider_args
  : STRING_LITERAL COMMA! float_or_dec COMMA! float_or_dec COMMA! float_or_dec COMMA! float_or_dec
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
  
block
  : CLBRACKET statement* CRBRACKET -> ^(BLOCK statement*)
  ;
  
draw_mode
  : (COLON IPNS) -> ^(IPNS)
  | (COLON OPNS) -> ^(OPNS)
  ;
    
expression_statement
  : SEMICOLON!
  | (QUESTIONMARK^ | COLON^)? expression SEMICOLON!
  ;
  
if_statement 
  : IF LBRACKET condition=logical_or_expression RBRACKET     // condition
    then_part=statement                                      // then-part
    (else_part)?                                             // optional else-part
    -> ^(IF $condition $then_part else_part?)                      
  ;
  
else_part
  : ELSE block -> ^(ELSE block)
  | ELSE stmt=if_statement -> ^(ELSEIF $stmt)
  ;
  
loop
  : (PRAGMA UNROLL_LITERAL number=DECIMAL_LITERAL)? LOOP stmt=statement -> ^(LOOP $stmt $number?)
  ;
