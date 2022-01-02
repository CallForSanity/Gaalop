package de.gaalop.clucalc.input;

import de.gaalop.cfg.ControlFlowGraph;

import java.util.Collections;
import java.util.ArrayList;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
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
        AssignmentNode node = (AssignmentNode) visitAssignment(ctx.val);
        graph.addPragmaOnlyEvaluateNode(node);
        return node;
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
            AssignmentNode node = new AssignmentNode(graph, variable, expression);
            macroNodes.add(node);
            return node;
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
    public Object visitPRAGMARANGE(CluCalcParser.PRAGMARANGEContext ctx) {
        graphBuilder.addPragmaMinMaxValues(ctx.var.getText(), ctx.min.getText(), ctx.max.getText());
        return null;
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