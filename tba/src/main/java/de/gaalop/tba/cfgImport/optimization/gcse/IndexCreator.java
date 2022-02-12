package de.gaalop.tba.cfgImport.optimization.gcse;

import de.gaalop.StringList;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.Equality;
import de.gaalop.dfg.Exponentiation;
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
import java.util.LinkedList;

/**
 * Implements an CFG/DFG visitor to create an index of expressions
 * @author CSteinmetz15
 */
public class IndexCreator extends EmptyControlFlowVisitor implements ExpressionVisitor {

    private SequentialNode curNode;
    
    private final Index index = new Index();
    
    private StringList curPattern;
    
    /**
     * Creates an index of a ControlFlowGraph
     * @param graph The graph to create the index from
     * @return The index
     */
    public static Index createIndex(ControlFlowGraph graph) {
        LinkedList<HashExpression> hashExpressions = new LinkedList<>();
        
        IndexCreator visitor = new IndexCreator();
        graph.accept(visitor);
        return visitor.index; 
    }

    @Override
    public void visit(AssignmentNode node) {
        this.curNode = node;
        node.getValue().accept(this);
        index.indexExpression(node.getValue(), curPattern, curNode, node.getVariable());
        curPattern = null;
        super.visit(node);
    }

    @Override
    public void visit(ExpressionStatement node) {
        this.curNode = node;
        node.getExpression().accept(this);
        index.indexExpression(node.getExpression(), curPattern, curNode, null);
        curPattern = null;
        super.visit(node);
    }

    @Override
    public void visit(ColorNode node) {
        this.curNode = node;
        node.getR().accept(this);
        index.indexExpression(node.getR(), curPattern, curNode, null);
        node.getG().accept(this);
        index.indexExpression(node.getG(), curPattern, curNode, null);
        node.getB().accept(this);
        index.indexExpression(node.getB(), curPattern, curNode, null);
        node.getAlpha().accept(this);
        index.indexExpression(node.getAlpha(), curPattern, curNode, null);
        super.visit(node);
    }

    @Override
    public void visit(Addition node) {
        // Left
        node.getLeft().accept(this);
        index.indexExpression(node.getLeft(), curPattern, curNode, null);
        StringList leftPattern = curPattern;
        curPattern = null;
        
        // Right
        node.getRight().accept(this);
        index.indexExpression(node.getRight(), curPattern, curNode, null);
        
        curPattern.addAll(leftPattern);
        curPattern.add("+");
    }
    
    @Override
    public void visit(Subtraction node) {
        // Left
        node.getLeft().accept(this);
        index.indexExpression(node.getLeft(), curPattern, curNode, null);
        StringList leftPattern = curPattern;
        curPattern = null;
        
        // Right
        node.getRight().accept(this);
        index.indexExpression(node.getRight(), curPattern, curNode, null);
        
        curPattern.addAll(leftPattern);
        curPattern.add("-");
    }

    @Override
    public void visit(Division node) {
        // Left
        node.getLeft().accept(this);
        index.indexExpression(node.getLeft(), curPattern, curNode, null);
        StringList leftPattern = curPattern;
        curPattern = null;
        
        // Right
        node.getRight().accept(this);
        index.indexExpression(node.getRight(), curPattern, curNode, null);
        
        curPattern.addAll(leftPattern);
        curPattern.add("/");
    }

    @Override
    public void visit(Multiplication node) {
        // Left
        node.getLeft().accept(this);
        index.indexExpression(node.getLeft(), curPattern, curNode, null);
        StringList leftPattern = curPattern;
        curPattern = null;
        
        // Right
        node.getRight().accept(this);
        index.indexExpression(node.getRight(), curPattern, curNode, null);
        
        curPattern.addAll(leftPattern);
        curPattern.add("*");
    }
    
    @Override
    public void visit(Exponentiation node) {
        // Left
        node.getLeft().accept(this);
        index.indexExpression(node.getLeft(), curPattern, curNode, null);
        StringList leftPattern = curPattern;
        curPattern = null;
        
        // Right
        node.getRight().accept(this);
        index.indexExpression(node.getRight(), curPattern, curNode, null);
        
        curPattern.addAll(leftPattern);
        curPattern.add("^^");
    }

    @Override
    public void visit(MathFunctionCall node) {
        node.getOperand().accept(this);
        index.indexExpression(node.getOperand(), curPattern, curNode, null);
        curPattern.add(node.getFunction().name());
    }
    
    @Override
    public void visit(Negation node) {
        node.getOperand().accept(this);
        index.indexExpression(node.getOperand(), curPattern, curNode, null);
        curPattern.add("NEG");
    }
    
    // ===== Terminals =====

    @Override
    public void visit(Variable node) {
        curPattern = new StringList();
        curPattern.add(node.getName());
    }

    @Override
    public void visit(MultivectorComponent node) {
        curPattern = new StringList();
        curPattern.add(node.toString());
    }

    @Override
    public void visit(FloatConstant node) {
        curPattern = new StringList();
        curPattern.add(Double.toString(node.getValue()));
    }

    // ===== Illegal methods =====
    
    @Override
    public void visit(InnerProduct node) {
        throw new IllegalStateException();
    }
    
    @Override
    public void visit(OuterProduct node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(BaseVector node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(Reverse node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(LogicalOr node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(LogicalAnd node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(LogicalNegation node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(Equality node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(Inequality node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(Relation relation) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException();
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException();
    }
}
