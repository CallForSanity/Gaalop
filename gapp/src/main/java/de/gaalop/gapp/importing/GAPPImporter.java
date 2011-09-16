package de.gaalop.gapp.importing;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EmptyControlFlowVisitor;
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
import de.gaalop.gapp.GAPP;

/**
 * The new GAPPImporter
 * @author Christian Steinmetz
 */
public class GAPPImporter extends EmptyControlFlowVisitor implements ExpressionVisitor {

    private GAPP curGAPP;
    private MultivectorComponent curDestMvComp; // the current destination multivector component

    @Override
    public void visit(AssignmentNode node) {
        // create a new GAPP object and set it as member
        curGAPP = new GAPP();
        node.setGAPP(curGAPP);

        // remember the current destination multivector component
        curDestMvComp = (MultivectorComponent) node.getVariable();

        // process expression
        node.getValue().accept(this);

        // go on in graph
        super.visit(node);
    }

    @Override
    public void visit(Subtraction node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Addition node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Division node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Multiplication node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(MathFunctionCall node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Variable node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(MultivectorComponent node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Exponentiation node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(FloatConstant node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Negation node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // ============================ Logical methods ============================

    @Override
    public void visit(LogicalOr node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(LogicalAnd node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(LogicalNegation node) {
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

    // ========================= Illegal visit methods =========================

    @Override
    public void visit(InnerProduct node) {
        throw new IllegalStateException("InnerProducts should have been removed by TBA.");
    }

    @Override
    public void visit(OuterProduct node) {
        throw new IllegalStateException("OuterProducts should have been removed by TBA.");
    }

    @Override
    public void visit(BaseVector node) {
        throw new IllegalStateException("BaseVectors should have been removed by TBA.");
    }

    @Override
    public void visit(Reverse node) {
        throw new IllegalStateException("Reverses should have been removed by TBA.");
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("FunctionArguments should have been removed by CLUCalc Parser.");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("FunctionArguments should have been removed by CLUCalc Parser.");
    }



}
