package de.gaalop;

import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
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
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.OuterProduct;
import de.gaalop.dfg.Relation;
import de.gaalop.dfg.Reverse;
import de.gaalop.dfg.Subtraction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Base class of a default code generator visitor that can be used for easy creation of CodeGenerators.
 * This class implements common functionality for code generators that facilitates Code Generator implementations.
 * @author Christian Steinmetz
 */
public abstract class DefaultCodeGeneratorVisitor implements ControlFlowVisitor, ExpressionVisitor {

    protected Log log = LogFactory.getLog(DefaultCodeGeneratorVisitor.class);
    
    protected StringBuilder code = new StringBuilder();

    protected ControlFlowGraph graph = null;
    
    protected OperatorPriority operatorPriority;

    protected int indentation = 0;
    
    public DefaultCodeGeneratorVisitor() {
        this(new OperatorPriority());
    }

    public DefaultCodeGeneratorVisitor(OperatorPriority operatorPriority) {
        this.operatorPriority = operatorPriority;
    }

    public String getCode() {
        return code.toString();
    }
    
    protected void appendIndentation() {
        for (int i = 0; i < indentation; ++i) {
            code.append('\t');
        }
    }

    protected void addChild(Expression parent, Expression child) {
        if (operatorPriority.hasLowerPriority(parent, child)) {
            code.append('(');
            child.accept(this);
            code.append(')');
        } else {
            child.accept(this);
        }
    }
    
    protected void addBinaryInfix(BinaryOperation op, String operator) {
        addChild(op, op.getLeft());
        code.append(operator);
        addChild(op, op.getRight());
    }
    
    @Override
    public void visit(Addition addition) {
        addBinaryInfix(addition, " + ");
    }
    
    @Override
    public void visit(Subtraction subtraction) {
        addBinaryInfix(subtraction, " - ");
    }

    @Override
    public void visit(Multiplication multiplication) {
        addBinaryInfix(multiplication, " * ");
    }
        
    @Override
    public void visit(Division division) {
        addBinaryInfix(division, " / ");
    }

    /**
     * Checks if the given exponentiation is a square
     * @param exponentiation The exponentiation
     * @return true, if the given exponentiation is a square
     */
    protected boolean isSquare(Exponentiation exponentiation) {
        final FloatConstant two = new FloatConstant(2.0f);
        return two.equals(exponentiation.getRight());
    }

    @Override
    public void visit(IfThenElseNode node) {
        throw new IllegalStateException("IfThenElseNodes are not supported anymore.");
    }

    @Override
    public void visit(LoopNode node) {
        throw new IllegalStateException("IfThenElseNodes are not supported anymore.");
    }

    @Override
    public void visit(BreakNode breakNode) {
        throw new IllegalStateException("IfThenElseNodes are not supported anymore.");
    }

    @Override
    public void visit(BlockEndNode node) {
        throw new IllegalStateException("IfThenElseNodes are not supported anymore.");
    }
    
    @Override
    public void visit(InnerProduct innerProduct) {
        throw new UnsupportedOperationException("This backend does not support the inner product.");
    }
    
    @Override
    public void visit(OuterProduct outerProduct) {
        throw new UnsupportedOperationException("This backend does not support the outer product.");
    }

    @Override
    public void visit(BaseVector baseVector) {
        throw new UnsupportedOperationException("This backend does not support base vectors.");
    }

    @Override
    public void visit(Reverse node) {
        throw new UnsupportedOperationException("This backend does not support the reverse operation.");
    }

    @Override
    public void visit(LogicalOr node) {
        throw new IllegalStateException("Logical nodes are not supported anymore.");
    }

    @Override
    public void visit(LogicalAnd node) {
        throw new IllegalStateException("Logical nodes are not supported anymore.");
    }

    @Override
    public void visit(LogicalNegation node) {
        throw new IllegalStateException("Logical nodes are not supported anymore.");
    }

    @Override
    public void visit(Equality node) {
        throw new IllegalStateException("Logical nodes are not supported anymore.");
    }

    @Override
    public void visit(Inequality node) {
        throw new IllegalStateException("Logical nodes are not supported anymore.");
    }

    @Override
    public void visit(Relation relation) {
        throw new IllegalStateException("Logical nodes are not supported anymore.");
    }

    @Override
    public void visit(Macro node) {
        throw new IllegalStateException("Macros should have been inlined and removed from the graph.");
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("Macros should have been inlined and no function arguments should be the graph.");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("Macro "+node.getName()+" should have been inlined and no macro calls should be in the graph.");
    }
}
