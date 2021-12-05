package de.gaalop.quadriererOptimierer;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;




import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.Equality;
import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionVisitor;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.FunctionArgument;
import de.gaalop.dfg.Inequality;
import de.gaalop.dfg.InnerProduct;
import de.gaalop.dfg.LogicalAnd;
import de.gaalop.dfg.LogicalNegation;
import de.gaalop.dfg.LogicalOr;
import de.gaalop.dfg.MacroCall;
import de.gaalop.dfg.MathFunctionCall;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.OuterProduct;
import de.gaalop.dfg.Relation;
import de.gaalop.dfg.Reverse;
import de.gaalop.dfg.Subtraction;
import de.gaalop.dfg.Variable;
import java.util.HashMap;

/**
 *
 * @author pj
 */
public class Quadopt implements ExpressionVisitor, ControlFlowVisitor{
private ControlFlowGraph cfg;
private Expression nx;
private HashMap  <String,String> myHashmap = new HashMap();
AssignmentNode AddedExponentiationBang;
AssignmentNode currentAssignment;

    public Quadopt() {
    }




    public void handleBinaryNode (BinaryOperation node) {

       }

    //dfg
    public void visit(Subtraction node) {
        node.getLeft().accept(this);
        Expression lhs =nx;
        node.getRight().accept(this);
        Expression rhs =nx;
        nx = new Subtraction(lhs, rhs);

    }

    @Override //dfg
    public void visit(Addition node) {
    node.getLeft().accept(this);
        Expression lhs =nx;
        node.getRight().accept(this);
        Expression rhs =nx;
        nx = new Addition(lhs, rhs);
    }

    @Override//dfg
    public void visit(Division node) {
         node.getLeft().accept(this);
        Expression lhs =nx;
        node.getRight().accept(this);
        Expression rhs =nx;
        nx = new Division(lhs, rhs);
    }

    

    @Override //dfg
    public void visit(InnerProduct node) {
  //do nothing
    }

    @Override//dfg
    public void visit(Multiplication node) {
        node.getLeft().accept(this);
        Expression lhs =nx;
        node.getRight().accept(this);
        Expression rhs =nx;
        nx = new Multiplication(lhs, rhs);
    }

    @Override//dfg
    public void visit(MathFunctionCall node) {
  //TODO Schluckt nachkommende werte
        nx  = node;
    }

    @Override//dfg
    public void visit(Variable node) {
    nx  = node;
    }

    @Override//dfg
    public void visit(MultivectorComponent node) {
    nx  = node;
    }

    @Override//dfg
    public void visit(Exponentiation node) {
        if (isSquare(node) && (node.getLeft() instanceof Variable))    {
            String varname = null;
            if(node.getLeft() instanceof MultivectorComponent){
                varname = ((MultivectorComponent)node.getLeft()).getName() + ((MultivectorComponent)node.getLeft()).getBladeIndex();
            } else
                varname=  ((Variable)node.getLeft()).getName();
    
            if (!myHashmap.containsKey(varname)){
            myHashmap.put(varname, ( varname + "_quad"));

            AddedExponentiationBang = new AssignmentNode(cfg, new Variable(varname+"_quad"), new Multiplication(node.getLeft(), node.getLeft()));
                    //new AssignmentNode(cfg,new Variable(varname+"_quad"),node.copy());
            currentAssignment.insertBefore(AddedExponentiationBang);
           }

            nx = new Variable(myHashmap.get(varname));

            




        

        }
        else // left is novariable or node is no square
        {
        node.getLeft().accept(this);
        Expression lhs =nx;
        node.getRight().accept(this);
        Expression rhs =nx;
        nx = new Exponentiation(lhs, rhs);

        }

   
    }





    @Override//dfg
    public void visit(FloatConstant node) {
     nx  = node;
    }

    @Override //dfg
    public void visit(OuterProduct node) {
       nx  = node;
    }

    @Override //dfg
    public void visit(BaseVector node) {
    nx  = node;
    }

    @Override //dfg
    public void visit(Negation node) {
        node .getOperand().accept(this);
      // gefixt ...war schrott:   nx  = node;
        nx = new Negation(nx);
    }

    @Override //dfg
    public void visit(Reverse node) {
          nx  = node;
    }

    @Override //dfg
    public void visit(LogicalOr node) {
      nx  = node;
    }

    @Override //dfg
    public void visit(LogicalAnd node) {
      nx  = node;
    }

    @Override //dfg
    public void visit(Equality node) {
      nx  = node;
    }

    @Override //dfg
    public void visit(Inequality node) {
     nx  = node;
    }

    @Override //dfg
    public void visit(Relation relation) {
    throw new UnsupportedOperationException("Durchlauf des Quadopt wegen unsuporteter Operation gestoppt");
    }

    @Override
    public void visit(StartNode node) {
               System.out.println("Starting Quadopt");
              this.cfg = node.getGraph();
             node.getSuccessor().accept(this);
    }

    @Override
    public void visit(AssignmentNode node) {
        currentAssignment = node;
        node.getValue().accept(this);
        node.setValue(nx);
        node.getSuccessor().accept(this);

      
    }

    @Override
    public void visit(StoreResultNode node) {
               node.getSuccessor().accept(this);
    }

    @Override
    public void visit(IfThenElseNode node) {
              node.getSuccessor().accept(this);
    }

    @Override
    public void visit(BlockEndNode node) {
       node.getSuccessor().accept(this);
    }

    @Override
    public void visit(EndNode node) {
        System.out.println("QuadOpt erfolgreich beendet");
        
    }

	private boolean isSquare(Exponentiation exponentiation) {
	        final FloatConstant two = new FloatConstant(2.0f);
	        return two.equals(exponentiation.getRight());
	    }

    @Override
    public void visit(LogicalNegation node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(MacroCall node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(LoopNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(BreakNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Macro node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(ExpressionStatement node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	@Override
	public void visit(ColorNode node) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
