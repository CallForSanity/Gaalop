parser grammar TestbenchParser;

options {
  language = Java;
  tokenVocab = TestbenchLexer;
}

@header {
  package de.gaalop.testbench;
}

@members {
  private CoeffReader reader = new CoeffReader();
  
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

line returns [CoeffReader reader]
  @init { $reader = this.reader; }
  : coefficient*
  ;
  
coefficient 
  : (sign=(PLUS | MINUS)? coeff=number (WEDGE b=blade)?) {
    reader.addCoeff($sign.text, $coeff.text, $b.text);
  }
  ;
  
fragment number
  	:	DECIMAL_LITERAL
  	|	FLOATING_POINT_LITERAL
  	;

fragment blade 
  : blade_string
  | LBRACKET blade_string RBRACKET;
 
fragment blade_string
  : LETTER DECIMAL_LITERAL? (WEDGE LETTER DECIMAL_LITERAL)?;
