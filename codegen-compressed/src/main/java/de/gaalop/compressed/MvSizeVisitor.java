package de.gaalop.compressed;

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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Patrick Charrier
 */
public class MvSizeVisitor implements ControlFlowVisitor, ExpressionVisitor {

    protected Map<String,Integer> mvSizes = new HashMap<String,Integer>();

    public Map<String, Integer> getMvSizes() {
        return mvSizes;
    }
    
    @Override
    public void visit(StartNode node) {
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(AssignmentNode node) {
        MultivectorComponent mvComp = (MultivectorComponent)node.getVariable();
        if(mvComp == null)
            return;
        
        Integer numEntries = mvSizes.get(mvComp.getName());
        if(numEntries == null)
            numEntries = 1;
        else
            ++numEntries;
        
        mvSizes.put(mvComp.getName(), numEntries);

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
    public void visit(LoopNode node) {
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(BreakNode node) {
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(Macro node) {
    }

    @Override
    public void visit(ExpressionStatement node) {
    }

    @Override
    public void visit(EndNode node) {
    }

    @Override
    public void visit(ColorNode node) {
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(Subtraction node) {
    }

    @Override
    public void visit(Addition node) {
    }

    @Override
    public void visit(Division node) {
    }

    @Override
    public void visit(InnerProduct node) {
    }

    @Override
    public void visit(Multiplication node) {
    }

    @Override
    public void visit(MathFunctionCall node) {
    }

    @Override
    public void visit(Variable node) {
    }

    @Override
    public void visit(MultivectorComponent node) {
    }

    @Override
    public void visit(Exponentiation node) {
    }

    @Override
    public void visit(FloatConstant node) {
    }

    @Override
    public void visit(OuterProduct node) {
    }

    @Override
    public void visit(BaseVector node) {
    }

    @Override
    public void visit(Negation node) {
    }

    @Override
    public void visit(Reverse node) {
    }

    @Override
    public void visit(LogicalOr node) {
    }

    @Override
    public void visit(LogicalAnd node) {
    }

    @Override
    public void visit(LogicalNegation node) {
    }

    @Override
    public void visit(Equality node) {
    }

    @Override
    public void visit(Inequality node) {
    }

    @Override
    public void visit(Relation relation) {
    }

    @Override
    public void visit(FunctionArgument node) {
    }

    @Override
    public void visit(MacroCall node) {
    }
    
}
