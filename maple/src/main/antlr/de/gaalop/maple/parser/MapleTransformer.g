tree grammar MapleTransformer;

options {
	ASTLabelType = CommonTree;
	tokenVocab = MapleParser;
}

@header {
	package de.gaalop.maple.parser;
	
	import java.util.List;
	import java.util.ArrayList;
	import de.gaalop.cfg.*;
	import de.gaalop.dfg.*;
}

@members {	
	// Creates an expression from an identifier and takes constants into account
	private Expression processIdentifier(String name) {
		return new Variable(name);
	}
	
	private Expression processFunction(String name, ArrayList<Expression> args) {
		System.out.println("FUNCTION: " + name);
		for (Object obj : args) {
			System.out.println("ARG: " + args);
			System.out.println("ARG-Class: " + args.getClass());
		}
		return null;			
	}
}

script[ControlFlowGraph graph]	returns [List<SequentialNode> nodes] 
	@init { $nodes = new ArrayList<SequentialNode>(); }
 	: statement[graph, nodes]*;

statement[ControlFlowGraph graph, List<SequentialNode> nodes]
	: declareArray[graph]
	| coefficient[graph] { $nodes.add($coefficient.result); }
	;
	
declareArray[ControlFlowGraph graph]
	: ^(DECLAREARRAY name=IDENTIFIER) { /* What to do with declarations? Is it really needed? */ }
	;
	
coefficient[ControlFlowGraph graph] returns [SequentialNode result]
	: ^(COEFFICIENT mvName=IDENTIFIER index=DECIMAL_LITERAL value=expression) { 
		MultivectorComponent component = new MultivectorComponent($mvName.text.replaceAll("_opt",""), Integer.valueOf($index.text) - 1);
		$result = new AssignmentNode(graph, component, $value.result);
	 }
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
	| value=DECIMAL_LITERAL { $result = new FloatConstant(Float.parseFloat($value.text)); }
	// Floating Point Value (Constant)
	| value=FLOATING_POINT_LITERAL { $result = new FloatConstant(Float.parseFloat($value.text)); }
	// Reference to another multivector component (MV_SUBSCRIPT)
	| ^(MV_SUBSCRIPT name=IDENTIFIER index=DECIMAL_LITERAL) { $result = new MultivectorComponent($name.text.replaceAll("_opt",""), Integer.valueOf($index.text) - 1); }
	;

arguments returns [ArrayList<Expression> args] 
	@init { $args = new ArrayList<Expression>(); }
	: (arg=expression { $args.add($arg.result); })*
	;
