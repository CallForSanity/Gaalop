tree grammar GaaletTransformer;

options {
	ASTLabelType = CommonTree;
	tokenVocab = GaaletParser;
	backtrack = true;              // TODO: remove this (added because of multiple alternatives in statement_list rule)?
}

@header {
  package de.gaalop.gaalet.antlr;

	import java.util.Collections;	
	import java.util.ArrayList;
	import de.gaalop.cfg.*;
	import de.gaalop.dfg.*;
	import static de.gaalop.dfg.ExpressionFactory.*;
	import de.gaalop.gaalet.GraphBuilder;
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
 	
 	script  returns [ControlFlowGraph result] 
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
	: ^(PROCEDURE name=IDENTIFIER) { graphBuilder.handleProcedure($name.text); } // no CFG node needed for this type
	
	// Stand-alone assignment
	| assignment { $nodes.add(graphBuilder.handleAssignment($assignment.variable, $assignment.value)); }
	
	| define_assignment { 
	graphBuilder.wasDefined($define_assignment.variable);
	$nodes.add(graphBuilder.handleAssignment($define_assignment.variable, $define_assignment.value)); 
	}

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
  
	// Some other expression (We can ignore this since we don't implement side-effects)
	| expression {
	    Expression e = $expression.result;
	    if (e != null) { // null e.g. for procedure calls like DefVarsN3()
	      $nodes.add(graphBuilder.processExpressionStatement(e)); 
	    }     
	  }

  | pragma
  
  // the C C++ stuff 
  
  | ^(SET_OUTPUT variable){
    $nodes.add(graphBuilder.handlePrint($variable.result.copy()));
  }
  
  | ^(BLADE_ASSIGN name=IDENTIFIER blade=DECIMAL_LITERAL expression) {
           $nodes.add(graphBuilder.bladeAssignment($name.text, $blade.text, $expression.result));}  
  
  | ^(DEFINE_V variable) {   
           graphBuilder.wasDefined($variable.result);
           }
           
  |  ^(DEFINE_MV name=IDENTIFIER  gaalet_arguments_list){
    System.out.println("DEFINE_MV " + $name.text);
            graphBuilder.defineMV($name.text, $gaalet_arguments_list.args);
            }  
  |  ^(DEFINE_MV_AND_ASSIGN name=IDENTIFIER  gaalet_arguments_list value=expression){
  System.out.println("DEFINE_MV_AND_ASSIGN " + $name.text);
            graphBuilder.defineMV($name.text, $gaalet_arguments_list.args);
            $nodes.add(graphBuilder.handleAssignment($name.text, $value.result));        
            }              
           
  | ^(DEFINE_GEALG name=IDENTIFIER gaalet_arguments_list ARG_LIST_SEP arguments) {   
                     System.out.println("DEFINE_GEALG " + $name.text);
           $nodes.add(graphBuilder.handleDefGaalet($name.text, $gaalet_arguments_list.args, $arguments.args));
           }        
	;

gaalet_arguments_list returns [ArrayList<String> args] 
  @init { $args = new ArrayList<String>(); }
  : (arg=(DECIMAL_LITERAL | HEX) { $args.add($arg.text); })*
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
define_assignment returns [Variable variable, Expression value]
  : ^(DEFINE_ASSIGNMENT l=variable r=expression) {
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
  // Logical negation
  | ^(LOGICAL_NEGATION op=expression) { $result = new LogicalNegation($op.result); }  
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
	| ^(SINGLE_AND l=expression r=expression) { $result = new InnerProduct($l.result, $r.result); } //former DOT
	// Negation
	| ^(NEGATION op=expression) { $result = new Negation($op.result); }
	// Dual
	| ^(DUAL op=expression) { $result = graphBuilder.processFunction("*", Collections.singletonList($op.result)); }	
	// Reverse
	| ^(REVERSE op=expression) { $result = new Reverse($op.result); }
	
	// Inverse !A = ~A / A.~A 
  |	^(INVERSE op=expression) {
  $result = new Division(new Reverse($op.result), new InnerProduct($op.result, new Reverse($op.result)));}
	
	// Function Call
	| ^(FUNCTION name=IDENTIFIER arguments) { $result = graphBuilder.processFunction($name.text, $arguments.args); }
	| ^(FUNCTION name=IDENTIFIER ) { $result = graphBuilder.processFunction($name.text, null); }
	
  // blade 
  | ^(BLADE name=IDENTIFIER blade=DECIMAL_LITERAL) { $result = graphBuilder.blade($name.text, $blade.text);
     }	
	// Integral Value (Constant)
	| value=DECIMAL_LITERAL { $result = new FloatConstant($value.text); }
	// Floating Point Value (Constant)
	| value=FLOATING_POINT_LITERAL { $result = new FloatConstant($value.text); }
	// Variable or constantvariableOrConstant.result
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
