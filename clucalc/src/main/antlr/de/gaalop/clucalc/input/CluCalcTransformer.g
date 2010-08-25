tree grammar CluCalcTransformer;

options {
	ASTLabelType = CommonTree;
	tokenVocab = CluCalcParser;
}

@header {
	package de.gaalop.clucalc.input;

	import java.util.Collections;	
	import java.util.ArrayList;
	import de.gaalop.cfg.*;
	import de.gaalop.dfg.*;
	import static de.gaalop.dfg.ExpressionFactory.*;
}

@members {
	private GraphBuilder graphBuilder;
	private int inIfBlock = 0;
	private boolean inMacro = false;
	
	private static final class ParserError extends Error {
    public ParserError(String message) {
      super("Parser error: " + message);
    }
  }

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

script	returns [ControlFlowGraph result] 
	@init {
		graphBuilder = new GraphBuilder();
		result = graphBuilder.getGraph();
	}
	@after {
	  graphBuilder.finish();
	}
 	: statement*;

statement returns [ArrayList<SequentialNode> nodes]
	@init { $nodes = new ArrayList<SequentialNode>(); }
	// Print something to the console
	: ^(QUESTIONMARK assignment) {
		$nodes.add(graphBuilder.handleAssignment($assignment.variable, $assignment.value));
		$nodes.add(graphBuilder.handlePrint($assignment.variable.copy()));
	}
		
	| ^(QUESTIONMARK value=expression) { $nodes.add(graphBuilder.handlePrint($value.result)); }
	
	| IPNS { graphBuilder.handleNullSpace(NullSpace.IPNS); }
	
	| OPNS { graphBuilder.handleNullSpace(NullSpace.OPNS); }
	
	// Displayed assignment (ignore the display part)
	| ^(COLON assignment) { $nodes.add(graphBuilder.handleAssignment($assignment.variable, $assignment.value)); }
	
	| ^(COLON id=variableOrConstant) { $nodes.add(graphBuilder.processExpressionStatement($id.result)); }
	
	// Stand-alone assignment
	| assignment { $nodes.add(graphBuilder.handleAssignment($assignment.variable, $assignment.value)); }
	
	| block { $nodes = $block.nodes; }
	
	| if_statement { $nodes.add($if_statement.node);}
	
	| loop { $nodes.add($loop.node); }
	
	| BREAK {
	  if (inIfBlock > 0) { 
	    $nodes.add(graphBuilder.handleBreak());
	  } else {
	    throw new ParserError("A break command may only occur whithin a conditional statement.");
	  } 
	}

  | macro

  | pragma
  
  | slider
  
  | ^(COLOR arguments) {
      $nodes.add(graphBuilder.handleColor($arguments.args));
    }
    
  | ^(COLOR name=(BLACK | BLUE | CYAN | GREEN | MAGENTA | ORANGE | RED | WHITE | YELLOW)) {
      $nodes.add(graphBuilder.handleColor($name.text));  
  }
    
  | ^(BGCOLOR arguments) {
      graphBuilder.handleBGColor($arguments.args);
    }
 
	// Some single-line expression (without assignment), e.g. macro call 
	| expression {
	  Expression e = $expression.result;
	  if (e != null) { // null e.g. for procedure calls like DefVarsN3()
	    $nodes.add(graphBuilder.processExpressionStatement(e)); 
	  } 	  
	}
	;
	
macro
  @init { 
    if (inMacro) {
      throw new ParserError("A macro may only be defined in global scope.");
    }
    graphBuilder.beginNewScope(); 
    inMacro = true;
  }
  @after { 
    graphBuilder.endNewScope();
    inMacro = false;
  }
  : ^(MACRO id=IDENTIFIER { graphBuilder.addMacroName($id.text); } lst=statement_list e=return_value?) {
    graphBuilder.handleMacroDefinition($id.text, $lst.args, $e.result);
  }
  ;
  
return_value returns [Expression result]
  : ^(RETURN exp=expression) { $result = $exp.result; }
  ;

pragma
  :  PRAGMA RANGE_LITERAL min=float_literal LESS_OR_EQUAL varname=IDENTIFIER LESS_OR_EQUAL max=float_literal
     {  graphBuilder.addPragmaMinMaxValues($varname.text, min, max);}
   | PRAGMA OUTPUT_LITERAL varname=IDENTIFIER
     {  graphBuilder.addPragmaOutputVariable($varname.text);  }
   | PRAGMA IGNORE_LITERAL var=variable 
     {  graphBuilder.addIgnoreVariable($var.result);  }
  ;

assignment returns [Variable variable, Expression value]
	: ^(EQUALS l=variable r=expression) {
			$variable = $l.result;
			$value = $r.result;
	}
	;


variable returns [Variable result]
	: variableOrConstant {
		if ( !($variableOrConstant.result instanceof Variable) ) {
			throw new RecognitionException(input);
		}
		$result = (Variable)$variableOrConstant.result;
	}
	;
	
if_statement returns [IfThenElseNode node]
  @init { inIfBlock++; }
  @after { inIfBlock--; }
  : ^(IF condition=expression then_part=statement else_part=else_statement?) {
    $node = graphBuilder.handleIfStatement($condition.result, $then_part.nodes, $else_part.nodes);
  }
  ;
  
else_statement returns [ArrayList<SequentialNode> nodes]
  @init { 
    graphBuilder.beginNewScope();
    $nodes = new ArrayList<SequentialNode>(); 
  }
  @after { graphBuilder.endNewScope(); }
  : ^(ELSE block) { $nodes = $block.nodes; }
  | ^(ELSEIF if_statement) { 
    $if_statement.node.setElseIf(true);
    $nodes.add($if_statement.node); 
  }
  ;
  
loop returns [LoopNode node]
  : ^(LOOP stmt=statement number=DECIMAL_LITERAL? varname=variable?) {
      $node = graphBuilder.handleLoop($stmt.nodes, $number.text, $varname.result); 
   }
  ;
  
block returns [ArrayList<SequentialNode> nodes]
  @init { 
    graphBuilder.beginNewScope();
    $nodes = new ArrayList<SequentialNode>(); 
  }
  @after { graphBuilder.endNewScope(); }
  : ^(BLOCK stmts=statement_list) {
     $nodes.addAll($stmts.args);
  }
  ;
  
statement_list returns [ArrayList<SequentialNode> args] 
  @init { $args = new ArrayList<SequentialNode>(); }
  : (arg=statement { $args.addAll($arg.nodes); })*
  ;
  
slider
  : ^(SLIDER var=variable args=slider_args) {
      graphBuilder.handleSlider($var.result, $args.label, $args.min, $args.max, $args.step, $args.init);
  }
  ;
  
fragment slider_args returns [String label, double min, double max, double step, double init]
  : id=STRING_LITERAL COMMA mi=constant COMMA ma=constant COMMA st=constant COMMA in=constant {
      $label = $id.text.replaceAll("\"", "");
      $min = $mi.value;
      $max = $ma.value;
      $step = $st.value;
      $init = $in.value;
  }
  ;
  
fragment constant returns [double value]
  : decimal_literal { $value = Double.parseDouble($decimal_literal.result); }
  | float_literal { $value = Double.parseDouble($float_literal.result); }
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
	// Logical or
  | ^(DOUBLE_BAR l=expression r=expression) { $result = new LogicalOr($l.result, $r.result); }
  // Logical and
  | ^(DOUBLE_AND l=expression r=expression) { $result = new LogicalAnd($l.result, $r.result); }
	// Equality
  | ^(DOUBLE_EQUALS l=expression r=expression) { $result = new Equality($l.result, $r.result); }
	// Inequality	
  | ^(UNEQUAL l=expression r=expression) { $result = new Inequality($l.result, $r.result); }
	// Less than
  | ^(LESS l=expression r=expression) { $result = new Relation($l.result, $r.result, Relation.Type.LESS); }
	// Greater than
  | ^(GREATER l=expression r=expression) { $result = new Relation($l.result, $r.result, Relation.Type.GREATER); }
  // Less or equal
  | ^(LESS_OR_EQUAL l=expression r=expression) { $result = new Relation($l.result, $r.result, Relation.Type.LESS_OR_EQUAL); }
	// Greater or equal
  | ^(GREATER_OR_EQUAL l=expression r=expression) { $result = new Relation($l.result, $r.result, Relation.Type.GREATER_OR_EQUAL); }
	// Outer Product
	| ^(WEDGE l=expression r=expression) { $result = new OuterProduct($l.result, $r.result); }
	// Dot Product (Inner Product)
	| ^(DOT l=expression r=expression) { $result = new InnerProduct($l.result, $r.result); }
	// Negation
	| ^(NEGATION op=expression) { $result = new Negation($op.result); }
	// Dual
	| ^(DUAL op=expression) { $result = graphBuilder.processFunction("*", Collections.singletonList($op.result)); }	
	// Reverse
	| ^(REVERSE op=expression) { $result = new Reverse($op.result); }
	// Logical negation
	| ^(DOUBLE_NOT op=expression) { $result = new LogicalNegation($op.result); }
	// Function Call
	| ^(FUNCTION name=IDENTIFIER arguments) { $result = graphBuilder.processFunction($name.text, $arguments.args); }
	// Integral Value (Constant)
	| value=DECIMAL_LITERAL { $result = new FloatConstant($value.text); }
	// Floating Point Value (Constant)
	| value=FLOATING_POINT_LITERAL { $result = new FloatConstant($value.text); }
	// Function argument in macro
	| ^(ARGUMENT index=DECIMAL_LITERAL) { $result = new FunctionArgument(Integer.parseInt($index.text)); }
	// Variable or constant
	| variableOrConstant { $result = $variableOrConstant.result; }
	;

variableOrConstant returns [Expression result]
	: name=IDENTIFIER { $result = graphBuilder.processIdentifier($name.text); }
	;

arguments returns [ArrayList<Expression> args] 
	@init { $args = new ArrayList<Expression>(); }
	: (arg=expression { $args.add($arg.result); })*
	;

float_literal returns [String result]
  : sign=MINUS? val=FLOATING_POINT_LITERAL  {$result = new String((sign!=null?$sign.text:"") + $val.text);}
  ;

decimal_literal returns [String result]
  : sign=MINUS? val=DECIMAL_LITERAL  {$result = new String((sign!=null?$sign.text:"") + $val.text);}
  ;
