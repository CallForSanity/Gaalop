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
	private Variable processIdentifier(String name, HashMap<String, String> minVal, HashMap<String, String> maxVal) {
    Variable v = new Variable(name);
    if (minVal.containsKey(name)) {
      v.setMinValue(minVal.get(name));
    }
		if (maxVal.containsKey(name)) {
      v.setMaxValue(maxVal.get(name));
    }
		return v;
	}
	
	private Expression processFunction(String name, ArrayList<Expression> args) {
//		System.out.println("FUNCTION: " + name);
//		for (Object obj : args) {
//			System.out.println("ARG: " + args);
//			System.out.println("ARG-Class: " + args.getClass());
//		}
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
      throw new IllegalArgumentException("Function " + name + " is not supported by maple parser");
	  }
	}
}

script[ControlFlowGraph graph, HashMap<String, String> minVal, HashMap<String, String> maxVal]	returns [List<AssignmentNode> nodes]
	@init { $nodes = new ArrayList<AssignmentNode>(); }
 	: statement[graph, minVal, maxVal, nodes]*;

statement[ControlFlowGraph graph, HashMap<String, String> minVal, HashMap<String, String> maxVal, List<AssignmentNode> nodes]
	: declareArray[graph]
	| assignment[graph, minVal, maxVal] { $nodes.add($assignment.result);}
	| coefficient[graph, minVal, maxVal] { $nodes.add($coefficient.result); }
	;
	
assignment[ControlFlowGraph graph, HashMap<String, String> minVal, HashMap<String, String> maxVal] returns [AssignmentNode result]
  : ^(ASSIGN var=IDENTIFIER value=expression[minVal, maxVal]) {
    Variable variable = processIdentifier($var.text, minVal, maxVal);
    $result = new AssignmentNode(graph, variable, $value.result);
  }
  ;
	
declareArray[ControlFlowGraph graph]
	: ^(DECLAREARRAY name=IDENTIFIER) { /* What to do with declarations? Is it really needed? */ }
	;
	
coefficient[ControlFlowGraph graph, HashMap<String, String> minVal, HashMap<String, String> maxVal] returns [AssignmentNode result]
	: ^(COEFFICIENT mvName=IDENTIFIER index=DECIMAL_LITERAL value=expression[minVal, maxVal]) {
		MultivectorComponent component = new MultivectorComponent($mvName.text, Integer.valueOf($index.text) - 1);
		$result = new AssignmentNode(graph, component, $value.result);
	 }
	;

expression[HashMap<String, String> minVal, HashMap<String, String> maxVal] returns [Expression result]
	// Addition
	: ^(PLUS l=expression[minVal, maxVal] r=expression[minVal, maxVal]) { $result = new Addition($l.result, $r.result); }
	// Subtraction
	| ^(MINUS l=expression[minVal, maxVal] r=expression[minVal, maxVal]) { $result = new Subtraction($l.result, $r.result); }
	// Multiplication
	| ^(STAR l=expression[minVal, maxVal] r=expression[minVal, maxVal]) { $result = new Multiplication($l.result, $r.result); }
	// Division
	| ^(SLASH l=expression[minVal, maxVal] r=expression[minVal, maxVal]) { $result = new Division($l.result, $r.result); }
	// Exponentiation
	| ^(WEDGE l=expression[minVal, maxVal] r=expression[minVal, maxVal]) { $result = new Exponentiation($l.result, $r.result); }
	// Negation
	| ^(NEGATION v=expression[minVal, maxVal]) { $result = new Negation($v.result); }
	// Function Call
	| ^(FUNCTION name=IDENTIFIER arguments[minVal, maxVal]) { $result = processFunction($name.text, $arguments.args); }
	// Variable Reference
	| ^(VARIABLE name=IDENTIFIER) { $result = processIdentifier($name.text, minVal, maxVal); }
	// Integral Value (Constant)
	| value=DECIMAL_LITERAL { $result = new FloatConstant($value.text); }
	// Floating Point Value (Constant)
	| value=FLOATING_POINT_LITERAL { $result = new FloatConstant(java.lang.Float.parseFloat($value.text));}
	// Reference to another multivector component (MV_SUBSCRIPT)
	| ^(MV_SUBSCRIPT name=IDENTIFIER index=DECIMAL_LITERAL) { $result = new MultivectorComponent($name.text, Integer.valueOf($index.text) - 1); }
	;

arguments[HashMap<String, String> minVal, HashMap<String, String> maxVal] returns [ArrayList<Expression> args]
	@init { $args = new ArrayList<Expression>(); }
	: (arg=expression[minVal, maxVal] { $args.add($arg.result); })*
	;
