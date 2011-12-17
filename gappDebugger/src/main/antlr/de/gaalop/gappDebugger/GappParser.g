parser grammar GappParser;

options {
  output=AST;
  backtrack=true;
  memoize=true;
  tokenVocab=GappLexer;
}

@header {
  package de.gaalop.gappDebugger;
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
script
  : statement* EOF!
  ;

float_literal
  : MINUS? FLOATING_POINT_LITERAL
  ;

calcOperationType
  : CALC_OP_DIVISION_LITERAL
  | CALC_OP_EXPONENTIATION_LITERAL
  | CALC_OP_INVERT_LITERAL
  | CALC_OP_ABS_LITERAL
  | CALC_OP_ACOS_LITERAL
  | CALC_OP_ASIN_LITERAL
  | CALC_OP_ATAN_LITERAL
  | CALC_OP_CEIL_LITERAL
  | CALC_OP_COS_LITERAL
  | CALC_OP_EXP_LITERAL
  | CALC_OP_FACT_LITERAL
  | CALC_OP_FLOOR_LITERAL
  | CALC_OP_LOG_LITERAL
  | CALC_OP_SIN_LITERAL
  | CALC_OP_SQRT_LITERAL
  | CALC_OP_TAN_LITERAL
  ;

statement 
  : gappcommand SEMICOLON
  ;

multivector
  : IDENTIFIER
  ;

vector
  : IDENTIFIER
  ;

variableset
  : LSBRACKET multivector (COMMA multivector)* RSBRACKET
  ;

valueset
  : LSBRACKET float_literal (COMMA float_literal)* RSBRACKET
  ;

posSelector
  : DECIMAL_LITERAL
  ;

posSelectors
  : LSBRACKET posSelector (COMMA posSelector)* RSBRACKET
  ;

setOfVariables
  : IDENTIFIER
  ;

selector
  : MINUS DECIMAL_LITERAL
  | DECIMAL_LITERAL
  ;

selectors
  : LSBRACKET selector (COMMA selector)* RSBRACKET
  ;

pairSetOfVariablesAndIndices
  : setOfVariables selectors
  ;

argument
  : float_literal
  | pairSetOfVariablesAndIndices
  ;

listOfArguments
  : argument (COMMA argument)*
  ;

dotproduct
  : LESS vector (COMMA vector)* GREATER
  ;

gappcommand
  : ASSIGNINPUTSVECTOR_LITERAL IDENTIFIER EQUALS variableset
  | RESETMV_LITERAL multivector LSBRACKET DECIMAL_LITERAL RSBRACKET 
  | SETMV_LITERAL multivector posSelectors EQUALS setOfVariables selectors
  | SETVECTOR_LITERAL vector EQUALS CLBRACKET listOfArguments CRBRACKET
  | DOTVECTORS_LITERAL multivector LSBRACKET selector RSBRACKET EQUALS dotproduct 
  | ASSIGNMV_LITERAL multivector posSelectors EQUALS valueset
  | CALCULATEMVCOEFF_LITERAL multivector LSBRACKET DECIMAL_LITERAL RSBRACKET EQUALS calcOperationType LBRACKET multivector (COMMA multivector)? RBRACKET
  | CALCULATEMV_LITERAL multivector EQUALS calcOperationType LBRACKET multivector (COMMA multivector)? RBRACKET 
  ;