package de.gaalop.optimizations.CSE;

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
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.OuterProduct;
import de.gaalop.dfg.Relation;
import de.gaalop.dfg.Reverse;
import de.gaalop.dfg.Subtraction;
import de.gaalop.dfg.Variable;
import java.util.Iterator;

/**
 *
 * @author pj
 */
public class CSE_Replacer implements ExpressionVisitor,ControlFlowVisitor {

   Expression nx;
   ControlFlowGraph cfg;
   //AssignmentNode currentAssignment;
   OperationStore opstor;

    public CSE_Replacer(OperationStore opstor) {
        this.opstor = opstor;
    }

    @Override
    public void visit(Subtraction node) {
    if (opstor.OperationShouldBeReplaced(node)) {
          nx = new Variable(opstor.getReplacementID(node));
         System.out.println("Replacer: Node  in Set: (  " + node.toString() + "  )     ---> Replacing with " + opstor.getReplacementID(node));


        } else {
            node.getLeft().accept(this);
            Expression lhs = nx;
            node.getRight().accept(this);
            Expression rhs = nx;
            nx = new Subtraction(lhs, rhs);
            
          


        }
    }

    @Override
    public void visit(Addition node) {
     if (opstor.OperationShouldBeReplaced(node)) {
          nx = new Variable(opstor.getReplacementID(node));
         System.out.println("Replacer: Node  in Set: (  " + node.toString() + "  )    ---> Replacing with " + opstor.getReplacementID(node));


        } else {
            node.getLeft().accept(this);
            Expression lhs = nx;
            node.getRight().accept(this);
            Expression rhs = nx;
            nx = new Addition(lhs, rhs);




        }
    }
    @Override
    public void visit(Division node) {
   if (opstor.OperationShouldBeReplaced(node)) {
          nx = new Variable(opstor.getReplacementID(node));
         System.out.println("Replacer: Node  in Set: (  " + node.toString() + "  )     ---> Replacing with " + opstor.getReplacementID(node));


        } else {
            node.getLeft().accept(this);
            Expression lhs = nx;
            node.getRight().accept(this);
            Expression rhs = nx;
            nx = new Division(lhs, rhs);




        }
    }
    @Override
    public void visit(InnerProduct node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Multiplication node) {
     if (opstor.OperationShouldBeReplaced(node)) {
          nx = new Variable(opstor.getReplacementID(node));
         System.out.println("Replacer: Node  in Set: (  " + node.toString() + "  )    ---> Replacing with " + opstor.getReplacementID(node));


        } else {
            node.getLeft().accept(this);
            Expression lhs = nx;
            node.getRight().accept(this);
            Expression rhs = nx;
            nx = new Multiplication(lhs, rhs);




        }
    }
    @Override
    public void visit(MathFunctionCall node) {
        //TODO Schluckt nachkommende werte
          if (opstor.OperationShouldBeReplaced(node)) {
          nx = new Variable(opstor.getReplacementID(node));
         System.out.println("Replacer: Node  in Set: (  " + node.toString() + "  )    ---> Replacing with " + opstor.getReplacementID(node));


        } else {
        node.getOperand().accept(this);
        nx = new MathFunctionCall(nx, node.getFunction());



        }
        //nx = node;
    }

    @Override
    public void visit(Variable node) {
      nx = node;
    }

    @Override
    public void visit(MultivectorComponent node) {
      nx = node;
    }

    @Override
    public void visit(Exponentiation node) {
        Multiplication nodeMul;
        if (isSquare(node)) {
            nodeMul = new Multiplication(node.getLeft(), node.getLeft());
            nx = nodeMul;
            if (opstor.OperationShouldBeReplaced(nodeMul)) {
                nx = new Variable(opstor.getReplacementID(nodeMul));
            }

        } else {




            if (opstor.OperationShouldBeReplaced(node)) {
                nx = new Variable(opstor.getReplacementID(node));

                System.out.println("Replacer: Node  in Set: (  " + node.toString() + "  )    ---> Replacing with " + opstor.getReplacementID(node));


            } else {
                node.getLeft().accept(this);
                Expression lhs = nx;
                node.getRight().accept(this);
                Expression rhs = nx;
                nx = new Exponentiation(lhs, rhs);
            }

        }


    }

    @Override
    public void visit(FloatConstant node) {
  nx = node;
    }

    @Override
    public void visit(OuterProduct node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(BaseVector node) {
          throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Negation node) {
         node.getOperand().accept(this);
         nx= new Negation(nx);
    }

    @Override
    public void visit(Reverse node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(LogicalOr node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(LogicalAnd node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Equality node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Inequality node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Relation relation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(StartNode node) {


        System.out.println("Replacer: Starting CSE Replacement");
            this.cfg = node.getGraph();
        for (OperationToken iterable_element : opstor.ReplaceAbleOpsSet.values()) {

        cfg.addLocalVariable(new Variable(iterable_element.getIdentifier()));
             }
    
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(AssignmentNode node) {

        System.out.println("Replacer : Assginment Node " + node.getVariable().toString()+" visited------------------------------------");
    

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

        System.out.println("Replacer: CSE Replacement beendet");
        
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
	public void visit(ColorNode colorNode) {
		throw new UnsupportedOperationException("Not supported yet.");
	}



}
