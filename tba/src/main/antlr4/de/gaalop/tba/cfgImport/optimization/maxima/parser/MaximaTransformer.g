tree grammar MaximaTransformer;

options {
	ASTLabelType = CommonTree;
	tokenVocab = MaximaParser;
}

@header {
	package de.gaalop.tba.cfgImport.optimization.maxima.parser;
	
	import java.util.List;
	import java.util.ArrayList;
	import de.gaalop.cfg.*;
	import de.gaalop.dfg.*;
  import java.util.HashMap;

}

@members {	
	// Creates an expression from an identifier and takes constants into account
	private Variable processIdentifier(String name) {
            return new Variable(name);
	}
	
	private Expression processFunction(String name, ArrayList<Expression> args) {
    if (name.equals("abs")) {
      return new MathFunctionCall(args.get(0), MathFunction.ABS);
    } else {
      for (MathFunction mathFunction : MathFunction.values()) {
        if (mathFunction.toString().toLowerCase().equals(name)) {
          if (args.size() == 1) {
            return new MathFunctionCall(args.get(0), mathFunction);
          } else {
            throw new IllegalArgumentException("Trying to parse math function " + mathFunction + " with more than one"
                + " argument: " + args);
          }
        }
      }
      throw new IllegalArgumentException("Function " + name + " is not supported by maxima parser");
	  }
	}
}

script returns [Expression result]
        : expression
        ;

expression returns [Expression result]
	// Addition
	: ^(PLUS l=expression r=expression) { $result = new Addition($l.result, $r.result); }
	// Subtraction
	| ^(MINUS l=expression r=expression) { $result = new Subtraction($l.result, $r.result); }
	// Multiplication
	| ^(STAR l=expression r=expression) { $result = new Multiplication($l.result, $r.result); }
	// Division
	| ^(SLASH l=expression r=expression) { $result = new Division($l.result, $r.result); }
	// Exponentiation
	| ^(WEDGE l=expression r=expression) { $result = new Exponentiation($l.result, $r.result); }
	// Negation
	| ^(NEGATION v=expression) { $result = new Negation($v.result); }
	// Function Call
	| ^(FUNCTION name=IDENTIFIER arguments) { $result = processFunction($name.text, $arguments.args); }
	// Variable Reference
	| ^(VARIABLE name=IDENTIFIER) { $result = processIdentifier($name.text); }
	// Integral Value (Constant)
	| value=DECIMAL_LITERAL { $result = new FloatConstant($value.text); }
	// Floating Point Value (Constant)
	| value=FLOATING_POINT_LITERAL { $result = new FloatConstant(java.lang.Float.parseFloat($value.text));}
	// Reference to another multivector component (MV_SUBSCRIPT)
	| ^(MV_SUBSCRIPT name=IDENTIFIER index=DECIMAL_LITERAL) { $result = new MultivectorComponent($name.text, Integer.valueOf($index.text)); }
	;

arguments returns [ArrayList<Expression> args]
	@init { $args = new ArrayList<Expression>(); }
	: (arg=expression { $args.add($arg.result); })*
	;
