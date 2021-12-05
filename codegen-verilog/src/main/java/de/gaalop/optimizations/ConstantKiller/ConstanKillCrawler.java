package de.gaalop.optimizations.ConstantKiller;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ColorNode;
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
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author pj
 */
public class ConstanKillCrawler implements ControlFlowVisitor, ExpressionVisitor {

    HashMap<String, Double> oneset = new HashMap<String, Double>();
    private AssignmentNode currAssignmentNode = null;
    private String currAssignString;
    private Expression resultExpr;
    private Expression right;
    private Expression left;
    private Expression operand;
    int replacements=0;

    @Override
    public void visit(StartNode node) {
        System.out.println("Constant Kill Run starting \n \n \n");
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(AssignmentNode node) {
        // node.getVariable().getName();
        currAssignmentNode = node;
        currAssignString= node.getVariable().toString();
        resultExpr = null;
    
        if (( node.getValue() instanceof Negation) &&((Negation)node.getValue()).getOperand() instanceof FloatConstant ) {
       
         FloatConstant f = (FloatConstant)((Negation)node.getValue()).getOperand();
         node.setValue(new FloatConstant(-f.getValue()));
     }

        if ((node.getValue() instanceof FloatConstant)) //&&(new Float(1.0) == ((FloatConstant) node.getValue()).getValue()))
                {

            {
                oneset.put(node.getVariable().toString(), ((FloatConstant) node.getValue()).getValue());
                System.out.println("Found: " + node.getVariable().toString()+ " Value: " +((FloatConstant) node.getValue()).getValue() );
                //node.getVariable();
                node.getSuccessor().accept(this);

            }
        } else {
            node.getValue().accept(this);
            node.setValue(resultExpr);
            node.getSuccessor().accept(this);
        }





        //  throw new UnsupportedOperationException("Not supported yet.");
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
        System.out.println( " \n \n \n Constant Kill Run finished.....Replacements Made: "+ replacements+"  \n \n \n \n \n");
    }

	@Override
	public void visit(ColorNode node) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    @Override
    public void visit(Subtraction node) {
        handlebinary(node);
        resultExpr = new Subtraction(left, right);
         left=null;
        right=null;

    }

    @Override
    public void visit(Addition node) {
        handlebinary(node);
        resultExpr = new Addition(left, right);
         left=null;
        right=null;
    }

    @Override
    public void visit(Division node) {
        handlebinary(node);
        resultExpr = new Division(left, right);
        left=null;
        right=null;
    }

    public void handlebinary(BinaryOperation node) {
        Expression leftlocal;
        Expression rightlocal;


        node.getLeft().accept(this);
        leftlocal = resultExpr;
        node.getRight().accept(this);
        rightlocal = resultExpr;

        assert leftlocal != null && rightlocal != null;


        if (node.getLeft() instanceof MultivectorComponent && oneset.containsKey(((MultivectorComponent) node.getLeft()).toString())) {
            System.out.println("Assignment: "+ currAssignString+ "  -     Replaceable Left: " + ((MultivectorComponent) node.getLeft()).toString()+ "with:   "+ oneset.get(((MultivectorComponent)node.getLeft()).toString()));
            leftlocal = new FloatConstant(oneset.get(((MultivectorComponent)node.getLeft()).toString()));
             System.out.println("Bhm");
             replacements++;


        }

        if (node.getRight() instanceof MultivectorComponent && oneset.containsKey(((MultivectorComponent) node.getRight()).toString())) {
            System.out.println("Assignment: "+ currAssignString+ "  -     Replaceable Right: " + ((MultivectorComponent) node.getRight()).toString()+" with:  "+ oneset.get(((MultivectorComponent)node.getRight()).toString()));
            rightlocal = new FloatConstant(oneset.get(((MultivectorComponent)node.getRight()).toString()));
            System.out.println("Bhm");
            replacements++;

        }

        assert leftlocal != null && rightlocal != null;

        left=leftlocal;
        right=rightlocal;


    }

    @Override
    public void visit(Multiplication node) {
        handlebinary(node);
        resultExpr = new Multiplication(left, right);
         left=null;
        right=null;
    }

    @Override
    public void visit(InnerProduct node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(MathFunctionCall node) {
        node.getOperand().accept(this);
        resultExpr = new MathFunctionCall(resultExpr, node.getFunction());
    }

    @Override
    public void visit(Variable node) {
        resultExpr = node;
    }

    @Override
    public void visit(MultivectorComponent node) {
        if (oneset.containsKey(node.toString())) {
            resultExpr = new FloatConstant(oneset.get(node.toString()));
            replacements++;
        } else {
            resultExpr = node;
        }
    }

    @Override
    public void visit(Exponentiation node) {
        handlebinary(node);
        resultExpr = new Exponentiation(left, right);
    }

    @Override
    public void visit(FloatConstant node) {
        resultExpr = node;
    }

    @Override
    public void visit(OuterProduct node) {
        resultExpr = node;
    }

    @Override
    public void visit(BaseVector node) {
        resultExpr = node;
    }

    @Override
    public void visit(Negation node) {
         node.getOperand().accept(this);
        if (node.getOperand() instanceof MultivectorComponent && oneset.containsKey(((MultivectorComponent) node.getOperand()).toString())) {
            System.out.println("Assignment: "+ currAssignString+ "  -     Replaceable Negation Operand: " + ((MultivectorComponent) node.getOperand()).toString()+ "with:    "+ oneset.get(((MultivectorComponent)node.getOperand()).toString()));
            resultExpr = new FloatConstant(oneset.get(((MultivectorComponent)node.getOperand()).toString()));
            replacements++;

        }
   
        resultExpr = new Negation(resultExpr);
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
}
