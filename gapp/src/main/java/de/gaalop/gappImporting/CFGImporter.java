/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gappImporting;

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
import de.gaalop.dfg.UnaryOperation;
import de.gaalop.dfg.Variable;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.DFGVisitorImport;
import de.gaalop.tba.cfgImport.MvExpressions;
import java.util.HashMap;

/**
 * Decorates a ControlFlowGraph with a GAPPProgram
 * @author christian
 */
public class CFGImporter extends EmptyControlFlowVisitor implements ExpressionVisitor {

    private HashMap<Variable,GAPPMultivector> mapVariableGAPPMv = new HashMap<Variable, GAPPMultivector>();

    private GAPP curGAPP;

    private UseAlgebra usedAlgebra;

    private HashMap<Expression,MvExpressions> expressions;

    public CFGImporter(UseAlgebra usedAlgebra, HashMap<Expression,MvExpressions> expressions) {
        this.expressions = expressions;
        this.usedAlgebra = usedAlgebra;
    }

    private GAPPMultivector createGAPPMvFromVariable(Variable variable) {
        return new GAPPMultivector(variable.getName());
    }

    @Override
    public void visit(AssignmentNode node) {
        destination = createGAPPMvFromVariable(node.getVariable());
        curGAPP = new GAPP();
        node.setGAPP(curGAPP);
        node.getValue().accept(this);
        super.visit(node);
    }

    private GAPPMultivector destination;

    private Selectorset getSelectorSetAllBladesPositive() {
        Selectorset result = new Selectorset();
        for (int blade=0;blade<usedAlgebra.getBladeCount();blade++)
            result.add(new Integer(blade));
        return result;
    }

    private Selectorset getSelectorSetAllBladesNegative() {
        Selectorset result = new Selectorset();
        for (int blade=0;blade<usedAlgebra.getBladeCount();blade++)
            result.add(new Integer(-blade));
        return result;
    }

    private GAPPMultivector getGAPPMultivectorFromExpression(Expression expression) {
        //TODO chs
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void visit(Subtraction node) {
        curGAPP.addInstruction(new GAPPResetMv(destination));
        GAPPMultivector mvLeft = getGAPPMultivectorFromExpression(node.getLeft());
        GAPPMultivector mvRight = getGAPPMultivectorFromExpression(node.getRight());
        Selectorset selSet = getSelectorSetAllBladesPositive();
        curGAPP.addInstruction(new GAPPSetMv(destination, mvRight, getSelectorSetAllBladesNegative(), selSet));
        curGAPP.addInstruction(new GAPPAddMv(destination, mvLeft, selSet, selSet));
    }

    @Override
    public void visit(Addition node) {
        GAPPMultivector mvLeft = getGAPPMultivectorFromExpression(node.getLeft()); 
        GAPPMultivector mvRight = getGAPPMultivectorFromExpression(node.getRight()); 
        Selectorset selSet = getSelectorSetAllBladesPositive();

        curGAPP.addInstruction(new GAPPResetMv(destination));
        curGAPP.addInstruction(new GAPPSetMv(destination, mvLeft, selSet, selSet));
        curGAPP.addInstruction(new GAPPAddMv(destination, mvRight, selSet, selSet));
    }

    @Override
    public void visit(Division node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void handleGAProduct(byte typeProduct, BinaryOperation node) {
        //TODO chs 
        
    }

    @Override
    public void visit(InnerProduct node) {
        handleGAProduct(DFGVisitorImport.INNER, node);
    }

    @Override
    public void visit(OuterProduct node) {
        handleGAProduct(DFGVisitorImport.OUTER, node);
    }

    @Override
    public void visit(Multiplication node) {
        handleGAProduct(DFGVisitorImport.GEO, node);
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
    public void visit(BaseVector node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Negation node) {
        throw new UnsupportedOperationException("Not supported yet.");
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

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("Macros should have been inlined and no function arguments should be the graph.");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("Macros should have been inlined and no function arguments should be the graph.");
    }



}
