/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.gappImporting;

import de.gaalop.api.dfg.DFGNodeType;
import de.gaalop.api.dfg.DFGNodeTypeGetter;
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
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPScalarVariable;
import de.gaalop.gapp.variables.GAPPSignedMultivectorComponent;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.tba.Multivector;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.DFGVisitorImport;
import de.gaalop.tba.cfgImport.MvExpressions;
import de.gaalop.tba.cfgImport.optimization.ConstantFolding;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Decorates a ControlFlowGraph with a GAPPProgram
 * @author christian
 */
public class CFGImporter extends EmptyControlFlowVisitor implements ExpressionVisitor {

    
    private GAPP curGAPP;
    private UseAlgebra usedAlgebra;
    private final double EPSILON = 10E-07;

    private HashMap<Expression, MvExpressions> expressions; // from TBA

    private HashMap<Variable, GAPPMultivector> mapVariableGAPPMv = new HashMap<Variable, GAPPMultivector>();
    private HashMap<GAPPMultivector, BooleanArr> assigned = new HashMap<GAPPMultivector, BooleanArr>();

    private BooleanArr getBooleanArr(GAPPMultivector mv) {
        if (assigned.containsKey(mv))
            return assigned.get(mv);
        else {
            BooleanArr arr = new BooleanArr(usedAlgebra.getBladeCount());
            assigned.put(mv, arr);
            return arr;
        }

    }

    public CFGImporter(UseAlgebra usedAlgebra, HashMap<Expression, MvExpressions> expressions) {
        this.expressions = expressions;
        this.usedAlgebra = usedAlgebra;
    }

    private GAPPMultivector createGAPPMvFromVariable(Variable variable, boolean scalar,boolean assign) {

        GAPPMultivector mv = new GAPPMultivector(createNewName(true));

        MvExpressions expr = new MvExpressions(variable.getName(),usedAlgebra.getBladeCount());
        expr.bladeExpressions[0] = variable;

        if (assign)
            assignVectorFromMvExpressions(mv, expr);
        mapVariableGAPPMv.put(variable, mv);
        if (scalar)
            getBooleanArr(mv).setComponent(0, true);

        return mv;
    }

    @Override
    public void visit(AssignmentNode node) {
        curGAPP = new GAPP();
        destination = new GAPPMultivector(node.getVariable().getName());
        mapVariableGAPPMv.put(node.getVariable(), destination);

        node.setGAPP(curGAPP);
        node.getValue().accept(this);
        super.visit(node);
    }
    
    private GAPPMultivector destination;
    private int tmpCounter = 0;

    private String createNewName(boolean mv) {

        String prefix = (mv) ? "mTmp" : "vTmp";

        while (mapVariableGAPPMv.containsKey(new Variable((prefix + tmpCounter)))) 
            tmpCounter++;

        tmpCounter++;

        return prefix + (tmpCounter - 1);
    }

    private Selectorset getSelectorSetAllBladesPositive() {
        Selectorset result = new Selectorset();
        for (int blade = 0; blade < usedAlgebra.getBladeCount(); blade++) {
            result.add(new Integer(blade));
        }
        return result;
    }

    private Selectorset getSelectorSetAllBladesPositiveAndAssigned(GAPPMultivector mv) {
        BooleanArr arr = getBooleanArr(mv);
        Selectorset result = new Selectorset();
        for (int blade = 0; blade < usedAlgebra.getBladeCount(); blade++) {
            if (arr.getComponent(blade)) {
                result.add(new Integer(blade));
            }
        }

        return result;
    }

    private Selectorset getSelectorSetAllBladesNegative() {
        Selectorset result = new Selectorset();
        for (int blade = 0; blade < usedAlgebra.getBladeCount(); blade++) {
            result.add(new Integer(-blade));
        }
        return result;
    }

    private Selectorset getSelectorSetAllBladesNegativeAndAssigned(GAPPMultivector mv) {
        BooleanArr arr = getBooleanArr(mv);
        Selectorset result = new Selectorset();
        for (int blade = 0; blade < usedAlgebra.getBladeCount(); blade++) {
            if (arr.getComponent(blade)) {
                result.add(new Integer(-blade));
            }
        }

        return result;
    }

    private GAPPMultivector getGAPPMultivectorFromExpression(Expression expression) {

        DFGNodeType type = DFGNodeTypeGetter.getTypeOfDFGNode(expression);
        if (type != DFGNodeType.Variable) {
            MvExpressions mv = expressions.get(expression);
            // build the multivector
            GAPPMultivector newMv = new GAPPMultivector(createNewName(true));

            assignVectorFromMvExpressions(newMv, mv);

            return newMv;
        }

        return mapVariableGAPPMv.get((Variable) expression);
    }

    private void assignVectorFromMvExpressions(GAPPMultivector mv, MvExpressions mvExpr) {
        Selectorset sel = new Selectorset();
        Variableset val = new Variableset();


        BooleanArr arr = getBooleanArr(mv);

        //assign Values from mv
        for (int blade = 0; blade < usedAlgebra.getBladeCount(); blade++) {
            if (mvExpr.bladeExpressions[blade] != null) {
                arr.setComponent(blade, true);
                sel.add(blade);

                ConstantFolding folding = new ConstantFolding();
                mvExpr.bladeExpressions[blade].accept(folding);

                DFGNodeType typeBlade = DFGNodeTypeGetter.getTypeOfDFGNode(mvExpr.bladeExpressions[blade]);

                if (typeBlade == DFGNodeType.FloatConstant) {
                    val.add(new GAPPScalarVariable(((FloatConstant) mvExpr.bladeExpressions[blade]).getValue()));
                } else {

                    if (typeBlade == DFGNodeType.Variable) {
                        val.add(new GAPPScalarVariable(((Variable) mvExpr.bladeExpressions[blade]).getName()));
                    } else {
                         System.err.println(typeBlade + "isn't known in getGappmultivector()");
                    }
                }


            }
        }

        curGAPP.addInstruction(new GAPPResetMv(mv));
        curGAPP.addInstruction(new GAPPAssignMv(mv, sel, val));
    }

    @Override
    public void visit(Subtraction node) {
        GAPPMultivector mvLeft = getGAPPMultivectorFromExpression(node.getLeft());
        GAPPMultivector mvRight = getGAPPMultivectorFromExpression(node.getRight());

        Selectorset selSetL = getSelectorSetAllBladesPositiveAndAssigned(mvLeft);

        Selectorset selSetRp = getSelectorSetAllBladesPositiveAndAssigned(mvRight);
        Selectorset selSetRn = getSelectorSetAllBladesNegativeAndAssigned(mvRight);
        curGAPP.addInstruction(new GAPPResetMv(destination));
        curGAPP.addInstruction(new GAPPSetMv(destination, mvLeft, selSetL, selSetL));
        curGAPP.addInstruction(new GAPPAddMv(destination, mvRight, selSetRp, selSetRn));

        BooleanArr boolArrDest = getBooleanArr(destination);
        boolArrDest.or(getBooleanArr(mvLeft));
        boolArrDest.or(getBooleanArr(mvRight));
    }

    @Override
    public void visit(Addition node) {
        GAPPMultivector mvLeft = getGAPPMultivectorFromExpression(node.getLeft());
        GAPPMultivector mvRight = getGAPPMultivectorFromExpression(node.getRight());

        Selectorset selSetL = getSelectorSetAllBladesPositiveAndAssigned(mvLeft);
        Selectorset selSetR = getSelectorSetAllBladesPositiveAndAssigned(mvRight);

        curGAPP.addInstruction(new GAPPResetMv(destination));
        curGAPP.addInstruction(new GAPPSetMv(destination, mvLeft, selSetL, selSetL));
        curGAPP.addInstruction(new GAPPAddMv(destination, mvRight, selSetR, selSetR));

        BooleanArr boolArrDest = getBooleanArr(destination);
        boolArrDest.or(getBooleanArr(mvLeft));
        boolArrDest.or(getBooleanArr(mvRight));
    }

    @Override
    public void visit(Division node) {
        //TODO chs only implement division of scalars
        System.err.println("Division isn't implemented yet");
    }

    /**
     * Handles a Geometric Algebra Product and produces code to represent it in curGAPP
     * @param typeProduct The type of the Geometric Algebra Product, defined in de.gaalop.tba.cfgImport.DVGVisitorImport
     * @param node The node, which represents the product in DFG
     */
    private void handleGAProduct(byte typeProduct, BinaryOperation node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);

        Triples[] triplesArr = getTriplesArr(typeProduct, node);

        curGAPP.addInstruction(new GAPPResetMv(destination));

        GAPPMultivector mvL = getGAPPMultivectorFromExpression(node.getLeft());
        GAPPMultivector mvR = getGAPPMultivectorFromExpression(node.getRight());

        for (int blade = 0; blade < usedAlgebra.getBladeCount(); blade++) 
            if (triplesArr[blade] != null && triplesArr[blade].size() > 0) {

                Selectorset sel1 = new Selectorset();
                Selectorset sel2 = new Selectorset();

                for (Triple curTriple : triplesArr[blade]) {
                    sel1.add(curTriple.getLeftBlade());
                    sel2.add(curTriple.getRightBlade() * ((int) curTriple.getSign()));
                }

                createCommandSeriesDotProd(destination, blade, mvL, mvR, sel1, sel2);
                getBooleanArr(destination).setComponent(blade, true);
            }
    }

    /**
     * Returns an array of Triples, which represents the not-null blades,
     * when multiplying node.getLeft() and node.getRight with a specified GA product.
     *
     * This method encapsulates the table-based-approach, that is used in pacakage de.gaalop.tba
     *
     * @param typeProduct The type of the Geometric Algebra Product, defined in de.gaalop.tba.cfgImport.DVGVisitorImport
     * @param node The node that stores the multiplication
     * @return The array of Triples
     */
    private Triples[] getTriplesArr(byte typeProduct, BinaryOperation node) {

        MvExpressions mvExprL = expressions.get(node.getLeft());
        MvExpressions mvExprR = expressions.get(node.getRight());

        Triples[] triplesArr = new Triples[usedAlgebra.getBladeCount()];

        for (int bladeL = 0; bladeL < usedAlgebra.getBladeCount(); bladeL++) {
            if (mvExprL.bladeExpressions[bladeL] != null) {
                for (int bladeR = 0; bladeR < usedAlgebra.getBladeCount(); bladeR++) {
                    if (mvExprR.bladeExpressions[bladeR] != null) {

                        Multivector out;
                        switch (typeProduct) {
                            case DFGVisitorImport.INNER:
                                out = usedAlgebra.inner(bladeL, bladeR);
                                break;
                            case DFGVisitorImport.OUTER:
                                out = usedAlgebra.outer(bladeL, bladeR);
                                break;
                            case DFGVisitorImport.GEO:
                                out = usedAlgebra.geo(bladeL, bladeR);
                                break;
                            default:
                                System.err.println("Product type is unknown!");
                                out = new Multivector(usedAlgebra.getAlgebra());
                                break;
                        }

                        double[] prod = out.getValueArr();

                        for (int bladeResult = 0; bladeResult < usedAlgebra.getBladeCount(); bladeResult++) {
                            if (Math.abs(prod[bladeResult]) > EPSILON) {

                                if (triplesArr[bladeResult] == null) {
                                    triplesArr[bladeResult] = new Triples();
                                }

                                triplesArr[bladeResult].add(new Triple(bladeL, bladeR, (byte) Math.signum(prod[bladeResult])));

                            }
                        }
                    }
                }
            }
        }

        return triplesArr;
    }

    /**
     * Create a command series in the curGAPP object for performing dotproduct of two multivector parts
     * @param destMv The destination multivector
     * @param destSel The blade index of destination multivector to store the result of dotproduct
     * @param src1 The first source multivector
     * @param src2 The second source multivector
     * @param sel1 The selector for first source multivector
     * @param sel2 The selector for second source multivector
     */
    private void createCommandSeriesDotProd(GAPPMultivector destMv, int destSel, GAPPMultivector src1, GAPPMultivector src2, Selectorset sel1, Selectorset sel2) {
        GAPPVector v1 = new GAPPVector(createNewName(false));
        GAPPVector v2 = new GAPPVector(createNewName(false));
        curGAPP.addInstruction(new GAPPSetVector(v1, src1, sel1));
        curGAPP.addInstruction(new GAPPSetVector(v2, src2, sel2));
        curGAPP.addInstruction(new GAPPDotVectors(destMv, destSel, v1, v2));
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
        System.err.println("MathFunctionCall Not supported yet.");
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Variable node) {
        if (!mapVariableGAPPMv.containsKey(node))
            createGAPPMvFromVariable(node,true,true);
    }

    @Override
    public void visit(MultivectorComponent node) {
        throw new IllegalStateException("MultivectorComponent aren't permitted in GAPP");
    }

    @Override
    public void visit(Exponentiation node) {
        System.err.println("Exponentiation Not supported yet.");
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(FloatConstant node) {
        //System.err.println("FloatConstant Not supported yet.");
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(BaseVector node) {
        //System.err.println("BaseVector Not supported yet.");
    }

    @Override
    public void visit(Negation node) {
        System.err.println("Negation Not supported yet.");
    }

    @Override
    public void visit(Reverse node) {
        System.err.println("Reverse Not supported yet.");
    }

    @Override
    public void visit(LogicalOr node) {
        System.err.println("LogicalOr Not supported yet.");
    }

    @Override
    public void visit(LogicalAnd node) {
        System.err.println("LogicalAnd Not supported yet.");
    }

    @Override
    public void visit(LogicalNegation node) {
        System.err.println("LogicalNegation Not supported yet.");
    }

    @Override
    public void visit(Equality node) {
        System.err.println("Equality Not supported yet.");
    }

    @Override
    public void visit(Inequality node) {
        System.err.println("Inequality Not supported yet.");
    }

    @Override
    public void visit(Relation relation) {
        System.err.println("Relation Not supported yet.");
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
