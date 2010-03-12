parser grammar CluCalcParser;

options {
  output=AST;
  backtrack=true;
  memoize=true;
  tokenVocab=CluCalcLexer;
}

tokens {
  FUNCTION;
  PROCEDURE;
  NEGATION;
  DUAL;
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

/*
  Expressions
*/
expression
  : lvalue EQUALS expression -> ^(EQUALS lvalue expression)
  | additive_expression
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
  ;
  
postfix_expression
  : primary_expression
  | function_call
  ;

function_call
  : (name=IDENTIFIER LBRACKET args=argument_expression_list RBRACKET)
  -> ^(FUNCTION $name $args)
  ;

primary_expression
  : IDENTIFIER
  | constant
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
  : expression_statement
  | procedure_call
  | draw_mode
  | if_statement
  ;
  
draw_mode
  : (COLON IPNS) -> ^(IPNS)
  | (COLON OPNS) -> ^(OPNS)
  ;
  
procedure_call
  : (name=IDENTIFIER LBRACKET RBRACKET) -> ^(PROCEDURE $name)
  ;
  
expression_statement
  : SEMICOLON!
  | (QUESTIONMARK^ | COLON^)? expression SEMICOLON!
  ;
  
if_statement 
  : IF LBRACKET condition=logical_or_expression RBRACKET                // condition
    CLBRACKET then_list=statement_list CRBRACKET                            // then-part
    (   ELSE CLBRACKET else_list=statement_list CRBRACKET                   // else-part (optional)
      | ELSE elseif=if_statement                                             // TODO: support elseif statements
    )?
    -> ^(IF $condition $then_list ELSE? $else_list? $elseif?)                      
  ;