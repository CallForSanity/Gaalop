/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.clucalc.input;

import de.gaalop.cfg.ControlFlowGraph;

import java.util.Collections;
import java.util.ArrayList;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
import static de.gaalop.dfg.ExpressionFactory.*;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Christian Steinmetz
 */
public class CluVisitor extends CluCalcBaseVisitor<Object> {

    public ControlFlowGraph graph;  //TODO Fill graph

    private ArrayList<SequentialNode> nodes;

    private GraphBuilder graphBuilder;
    private int inIfBlock = 0;
    private boolean inMacro = false;

    private static final class ParserError extends Error {

        public ParserError(String message) {
            super("Parser error: " + message);
        }
    }
    private List<String> errors = new ArrayList<String>();

    /*public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
		String hdr = getErrorHeader(e);
    String msg = getErrorMessage(e, tokenNames);
		errors.add(hdr + " " + msg);
	}
	public List<String> getErrors() {
		return errors;
	}*/

    @Override
    public Object visitEvaluateAssignmentCase(CluCalcParser.EvaluateAssignmentCaseContext ctx) {
        graph.getPragmaOnlyEvaluateVariables().add(((Variable) visit(ctx.val.var)).getName());
        return visitAssignment(ctx.val);
    }

    @Override
    public Object visitScript(CluCalcParser.ScriptContext ctx) {
        graphBuilder = new GraphBuilder();
        graph = graphBuilder.getGraph();
        nodes = new ArrayList<SequentialNode>();
        super.visitScript(ctx); //To change body of generated methods, choose Tools | Templates.
        graphBuilder.finish();
        return graph;
    }

    @Override
    public Object visitOutputAssignmentCaseC(CluCalcParser.OutputAssignmentCaseCContext ctx) {
        AssignmentNode assignment = (AssignmentNode) visit(ctx.val);
        
        ExpressionStatement ex = graphBuilder.processExpressionStatement(assignment.getVariable()); 
        if (inMacro)
            macroNodes.add(ex);
        else
            nodes.add(ex);
        
        graphBuilder.addVisualizerExpression(ex);   
        return null;
    }

    @Override
    public Object visitOutputVariableCaseC(CluCalcParser.OutputVariableCaseCContext ctx) {
        ExpressionStatement ex = graphBuilder.processExpressionStatement(new Variable(ctx.val.getText()));
        if (inMacro)
            macroNodes.add(ex);
        else
            nodes.add(ex);
        graphBuilder.addVisualizerExpression(ex);         
        return ex;
    }
    
    @Override
    public Object visitOutputAssignmentCaseQ(CluCalcParser.OutputAssignmentCaseQContext ctx) {
        AssignmentNode assignment = (AssignmentNode) visit(ctx.val);
        
        SequentialNode n2 = graphBuilder.handlePrint(assignment.getVariable().copy());
        if (inMacro)
            macroNodes.add(n2);
        else
            nodes.add(n2);
        return null; 
    }

    @Override
    public Object visitOutputVariableCaseQ(CluCalcParser.OutputVariableCaseQContext ctx) {
        StoreResultNode node = graphBuilder.handlePrint(new Variable(ctx.val.getText()));
        if (inMacro)
            macroNodes.add(node);
        else
            nodes.add(node);
        return node;
    }
    
    @Override
    public Object visitAssignment(CluCalcParser.AssignmentContext ctx) {
        Variable variable = (Variable) visit(ctx.var);
        Expression expression = (Expression) visit(ctx.expr);
        if (inMacro) {
            macroNodes.add(new AssignmentNode(graph, variable, expression));
            return null;
        } else
            return graphBuilder.handleAssignment(variable, expression);
    }

    @Override
    public Object visitVariable(CluCalcParser.VariableContext ctx) {
        return graphBuilder.processIdentifier(ctx.getText());
    }

    @Override
    public Object visitConstant(CluCalcParser.ConstantContext ctx) {
        return new FloatConstant(Double.parseDouble(ctx.getText()));
    }

    @Override
    public Object visitArgument(CluCalcParser.ArgumentContext ctx) {
        return new FunctionArgument(Integer.parseInt(ctx.index.getText()));
    }

    @Override
    public Object visitDual(CluCalcParser.DualContext ctx) {
        return graphBuilder.processFunction("*", Collections.singletonList((Expression) visit(ctx.operand)));
    }

    @Override
    public Object visitSingleArgument(CluCalcParser.SingleArgumentContext ctx) {
        LinkedList<Expression> result = new LinkedList<Expression>();
        result.add((Expression) visit(ctx.arg));
        return result;
    }

    @Override
    public Object visitMultipleArgument(CluCalcParser.MultipleArgumentContext ctx) {
        LinkedList<Expression> result = new LinkedList<Expression>();
        result.add((Expression) visit(ctx.arg));
        result.addAll((LinkedList<Expression>) visit(ctx.nextarg));
        return result;
    }

    @Override
    public Object visitFunction(CluCalcParser.FunctionContext ctx) {
        LinkedList<Expression> args = (ctx.args != null) ? (LinkedList<Expression>) visit(ctx.args) : new LinkedList<Expression>();
        return graphBuilder.processFunction(ctx.name.getText(), args);
    }
    
    @Override
    public Object visitCOLORARGS(CluCalcParser.COLORARGSContext ctx) {
        LinkedList<Expression> args = (ctx.args != null) ? (LinkedList<Expression>) visit(ctx.args) : new LinkedList<Expression>();
        nodes.add(graphBuilder.handleColor(args));
        return null;
    }

    @Override
    public Object visitBgcolor(CluCalcParser.BgcolorContext ctx) {
        LinkedList<Expression> args = (ctx.args != null) ? (LinkedList<Expression>) visit(ctx.args) : new LinkedList<Expression>();
        graphBuilder.handleBGColor(args);
        return null; 
    }

    private LinkedList<SequentialNode> macroNodes;

    @Override
    public Object visitMACRO(CluCalcParser.MACROContext ctx) {
        if (inMacro) {
          throw new ParserError("A macro may only be defined in global scope.");
        }
        graphBuilder.beginNewScope(); 
        inMacro = true;
        macroNodes = new LinkedList<>();

        graphBuilder.addMacroName(ctx.id.getText());
        

        if (ctx.body != null)
            visit(ctx.body);
        
        Expression e = null;
        if (ctx.e != null)
            e = (Expression) visit(ctx.e);

        graphBuilder.handleMacroDefinition(ctx.id.getText(), macroNodes, e);



        graphBuilder.endNewScope();
        inMacro = false;
        
        return null;
    }
    
    
    // Expressions

    @Override
    public Object visitAddition(CluCalcParser.AdditionContext ctx) {
        Expression l = (Expression) visit(ctx.left);
        Expression r = (Expression) visit(ctx.right);
        return new Addition(l,r);
    }

    @Override
    public Object visitSubtraction(CluCalcParser.SubtractionContext ctx) {
        return new Subtraction((Expression) visit(ctx.left), (Expression) visit(ctx.right));
    }

    @Override
    public Object visitMultiplication(CluCalcParser.MultiplicationContext ctx) {
        return new Multiplication((Expression) visit(ctx.left), (Expression) visit(ctx.right));
    }

    @Override
    public Object visitDivision(CluCalcParser.DivisionContext ctx) {
        return new Division((Expression) visit(ctx.left), (Expression) visit(ctx.right));
    }

    @Override
    public Object visitOuterProduct(CluCalcParser.OuterProductContext ctx) {
        return new OuterProduct((Expression) visit(ctx.left), (Expression) visit(ctx.right));
    }

    @Override
    public Object visitInnerProduct(CluCalcParser.InnerProductContext ctx) {
        return new InnerProduct((Expression) visit(ctx.left), (Expression) visit(ctx.right));
    }

    @Override
    public Object visitNegation(CluCalcParser.NegationContext ctx) {
        return new Negation((Expression) visit(ctx.operand));
    }

    @Override
    public Object visitReverse(CluCalcParser.ReverseContext ctx) {
        return new Reverse((Expression) visit(ctx.operand));
    }

    @Override
    public Object visitBracket(CluCalcParser.BracketContext ctx) {
        return (Expression) visit(ctx.e);
    }

    @Override
    public Object visitNegationBracket(CluCalcParser.NegationBracketContext ctx) {
        return new Negation((Expression) visit(ctx.operand));
    }
     
    @Override
    public Object visitCOLORNAME(CluCalcParser.COLORNAMEContext ctx) {
        nodes.add(graphBuilder.handleColor(ctx.name.getText()));
        return null;
    }

    @Override
    public Object visitNegationFunction(CluCalcParser.NegationFunctionContext ctx) {
        LinkedList<Expression> args = (ctx.args != null) ? (LinkedList<Expression>) visit(ctx.args) : new LinkedList<Expression>();
        return new Negation(graphBuilder.processFunction(ctx.name.getText(), args));
    }
    

}


/*


statement returns [ArrayList<SequentialNode> nodes]
	@init { $nodes = new ArrayList<SequentialNode>(); }
	// Print something to the console
	
	
	| IPNS { graphBuilder.handleNullSpace(NullSpace.IPNS); }
	
	| OPNS { graphBuilder.handleNullSpace(NullSpace.OPNS); }
	
	// Displayed assignment (ignore the display part)
	| ^(COLON assignment) { $nodes.add(graphBuilder.handleAssignment($assignment.variable, $assignment.value));
		ExpressionStatement ex = graphBuilder.processExpressionStatement($assignment.variable);
		$nodes.add(ex);
		graphBuilder.addVisualizerExpression(ex);
	}
	
	| ^(COLON id=variableOrConstant) { 
                ExpressionStatement ex = graphBuilder.processExpressionStatement($id.result);
		$nodes.add(ex);
		graphBuilder.addVisualizerExpression(ex);            
        }
	
	// Stand-alone assignment
	| assignment { $nodes.add(graphBuilder.handleAssignment($assignment.variable, $assignment.value)); }
	
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
  : ^(LOOP stmt=statement number=DECIMAL_LITERAL?) {
      $node = graphBuilder.handleLoop($stmt.nodes, $number.text); 
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

*/