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
import java.util.Iterator;

/**
 *
 * @author pj
 */
public class CSE_Collector implements ExpressionVisitor, ControlFlowVisitor {

  //  Expression nx;
    ControlFlowGraph cfg;
    AssignmentNode currentAssignment;
    OperationStore opstor;

    public OperationStore getOpstor() {
        return opstor;
    }
   

    //dfg
    public void handlebinary(BinaryOperation node) {

        if (opstor.add(node, currentAssignment)) {
            System.out.println("Collector: Node not in Set( " + node.toString() + " ) ---> Adding");
        } else {
            System.out.println("Collector: Node already in Set( " + node.toString() + " ) ");

        }

        node.getLeft().accept(this);
        node.getRight().accept(this);



    }

    @Override  //dfg
    public void visit(Addition node) {
        handlebinary(node);


    }

    @Override   //dfg
    public void visit(Division node) {
        handlebinary(node);

    }

    @Override  //dfg
    public void visit(InnerProduct node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override  //dfg
    public void visit(Multiplication node) {
        handlebinary(node);


    }

    @Override //dfg
    public void visit(Subtraction node) {
        handlebinary(node);
    }

    @Override  //dfg
    public void visit(MathFunctionCall node) {

         if (opstor.add(node, currentAssignment)) {
            System.out.println("Collector: Node not in Set( " + node.toString() + " ) ---> Adding");
        } else {
            System.out.println("Collector: Node already in Set( " + node.toString() + " ) ");

        }
        node.getOperand().accept(this);
    }

    @Override   //dfg
    public void visit(Variable node) {
        //donothing
    }

    @Override  //dfg
    public void visit(MultivectorComponent node) {
        //donothing
    }

    @Override  //dfg
    public void visit(Exponentiation node) {
        if(isSquare(node)){
         handlebinary(new Multiplication(node.getLeft(), node.getLeft()));

        }else{
        handlebinary(node);
        }
    }

    @Override  //dfg
    public void visit(FloatConstant node) {
        //donothing
    }

    @Override  //dfg
    public void visit(OuterProduct node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override  //dfg
    public void visit(BaseVector node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override  //dfg
    public void visit(Negation node) {




        node.getOperand().accept(this);

    }
//       if(opstor.add(node, currentAssignment))
//        {
//
//
//
//        }else if(opstor.containsOperation(node))
//        {
//            nx = new Variable(opstor.getReplacementID(node));}
//
//        System.err.println("Something in: " + node.getClass().getSimpleName() +" handling didnt work");
//    }

    @Override  //dfg
    public void visit(Reverse node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override  //dfg
    public void visit(LogicalOr node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override  //dfg
    public void visit(LogicalAnd node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override  //dfg
    public void visit(Equality node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override  //dfg
    public void visit(Inequality node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override  //dfg
    public void visit(Relation relation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override   //cfg
    public void visit(StartNode node) {
        System.out.println("Starting CSE Collections");
        opstor = new OperationStore();

        this.cfg = node.getGraph();
        node.getSuccessor().accept(this);
    }

    @Override   //cfg
    public void visit(AssignmentNode node) {
        System.out.println("Collector: Assginment Node " + node.getVariable().toString() + " vistited------------------------------------");
        currentAssignment = node;

        node.getValue().accept(this);
        node.getSuccessor().accept(this);

    }

    @Override   //cfg
    public void visit(StoreResultNode node) {
        node.getSuccessor().accept(this);
    }

    @Override   //cfg
    public void visit(IfThenElseNode node) {
        node.getSuccessor().accept(this);
    }

    @Override   //cfg
    public void visit(BlockEndNode node) {
        node.getSuccessor().accept(this);
    }

    @Override   //cfg
    public void visit(EndNode node) {
        System.out.println("CSE Collection erfolgreich beendet ---> Starte CSE Replacement");
        cfg.accept(new CSE_Replacer(opstor));
        opstor.printSummary();


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
