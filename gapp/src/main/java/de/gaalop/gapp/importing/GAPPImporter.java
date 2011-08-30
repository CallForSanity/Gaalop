package de.gaalop.gapp.importing;

import de.gaalop.api.dfg.DFGNodeType;
import de.gaalop.api.dfg.DFGNodeTypeGetter;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
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
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.instructionSet.CalculationType;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPConstant;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPScalarVariable;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.tba.Multivector;
import de.gaalop.tba.Products;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.MvExpressions;
import java.util.HashMap;

/**
 * Decorates a ControlFlowGraph with a GAPPProgram
 * @author christian
 */
public class GAPPImporter extends EmptyControlFlowVisitor implements ExpressionVisitor {

    private static final double EPSILON = 10E-07;

    /**
     * The current gapp object to insert new GAPP instructions
     */
    private GAPP curGAPP;

    /**
     * The used algebra
     */
    private UseAlgebra usedAlgebra;

    /**
     * The map from expressions to their mvExpressions,
     * calculated from the TBA plugin
     */
    private HashMap<Expression, MvExpressions> expressions;

    /**
     * Maps a variable to it's congruous GAPPMultivector
     */
    private HashMap<Variable, GAPPMultivector> mapVariableGAPPMv = new HashMap<Variable, GAPPMultivector>();

    /**
     * Stores the assigned components of a GAPPMultivector,
     * by using a map from GAPPMultivector to a boolean arr,
     * which components are true, when the component is assigned,
     * and false otherwise
     */
    public HashMap<GAPPMultivector, BooleanArr> assigned = new HashMap<GAPPMultivector, BooleanArr>();

    /**
     * The current destination GAPPMultivector
     */
    private GAPPMultivector destination;

    /**
     * The current counter for creating unique variable names
     */
    private int tmpCounter = 0;

    /**
     * Returns the accordingly array to a given GAPPMultivector in the assigned map.
     *
     * If such a assigned key don't exists, this methods creates a new boolean array,
     * stores it in the map, and returns it.
     *
     * @param mv The multivector which boolean arr should be returned
     * @return The accordingly boolean arr to the given multivector
     */
    private BooleanArr getBooleanArr(GAPPMultivector mv) {
        if (assigned.containsKey(mv))
            return assigned.get(mv);
        else {
            BooleanArr arr = new BooleanArr(usedAlgebra.getBladeCount());
            assigned.put(mv, arr);
            return arr;
        }
    }

    /**
     * Creates a new GAPPImporter instance
     * @param usedAlgebra The algebra which should be used
     * @param expressions The expressions map from the TBA pass
     */
    public GAPPImporter(UseAlgebra usedAlgebra, HashMap<Expression, MvExpressions> expressions) {
        this.expressions = expressions;
        this.usedAlgebra = usedAlgebra;
    }

    /**
     * Creates a new GAPPMultivector from a variable
     * @param variable The variable
     * @param scalar Sets the scalar component of the accordingly "assigned" boolean arr to true, if true
     * @param assign Assigns the values to the components of the multivector, if true
     * @return The new GAPPMultivector which is equivalent the given variable
     */
    private GAPPMultivector createGAPPMvFromVariable(Variable variable, boolean scalar, boolean assign) {

        GAPPMultivector mv = new GAPPMultivector(createNewName(true));

        MvExpressions expr = new MvExpressions(variable.getName(),usedAlgebra.getBladeCount());
        expr.bladeExpressions[0] = variable;

        if (assign)
            assignMvFromMvExpressions(mv, expr);
        mapVariableGAPPMv.put(variable, mv);
        if (scalar)
            getBooleanArr(mv).setComponent(0, true);

        return mv;
    }

    /**
     * Creates a new unique name for a multivector or vector variable
     * @param mv <value>true</value> if the new name refers to a multivector variable,
     *           <value>false</value> if it refers to a vector variable
     * @return The new unique name
     */
    private String createNewName(boolean mv) {

        String prefix = (mv) ? "mTmp" : "vTmp";

        while (mapVariableGAPPMv.containsKey(new Variable((prefix + tmpCounter))))
            tmpCounter++;

        tmpCounter++;

        return prefix + (tmpCounter - 1);
    }

    /**
     * Returns a new positive selectorset which contains all blades of a given multivector,
     * which are assigned.
     * @param mv The multivector
     * @return The selectorset that contains only the positive selectors where the assigned flag is set.
     */
    private Selectorset getSelectorsetAllBladesPositiveAndAssigned(GAPPMultivector mv) {
        BooleanArr arr = getBooleanArr(mv);
        Selectorset result = new Selectorset();
        for (int blade = 0; blade < usedAlgebra.getBladeCount(); blade++)
            if (arr.getComponent(blade)) 
                result.add(new Selector(new Integer(blade),(byte) 1));

        return result;
    }

    /**
     * Returns a new negative selectorset which contains all blades of a given multivector,
     * which are assigned.
     * @param mv The multivector
     * @return The selectorset that contains only the negative selectors where the assigned flag is set.
     */
    private Selectorset getSelectorsetAllBladesNegativeAndAssigned(GAPPMultivector mv) {
        BooleanArr arr = getBooleanArr(mv);
        Selectorset result = new Selectorset();
        for (int blade = 0; blade < usedAlgebra.getBladeCount(); blade++)
            if (arr.getComponent(blade))
                result.add(new Selector(new Integer(blade),(byte) -1));

        return result;
    }

    /**
     * Returns a new GAPPMultivector assigned with the values of a given expression.
     * Inserts new congruous GAPPInstructions in the current GAPP member.
     *
     * The assigned flags will be also modified by this method while transforming.
     *
     * @param expression The expression which should be transformed into an equivalent GAPPMultivector
     * @return The new GAPPMUltivector
     */
    private GAPPMultivector getGAPPMultivectorFromExpression(Expression expression) {

        DFGNodeType type = DFGNodeTypeGetter.getTypeOfDFGNode(expression);
        if (type != DFGNodeType.Variable) {
            MvExpressions mv = expressions.get(expression);

            // build the multivector
            GAPPMultivector newMv = new GAPPMultivector(createNewName(true));

            assignMvFromMvExpressions(newMv, mv);

            return newMv;
        }

        return mapVariableGAPPMv.get((Variable) expression);
    }

    /**
     * Assigns a mvExpressions object to a desination multivector.
     * Inserts new congruous GAPPInstructions in the current GAPP member.
     *
     * The assigned flags will be also modified by this method while transforming.
     *
     * @param mv The multivector which should be the destionation for the new GAPPInstructions
     * @param mvExpr The mvExpressions object
     */
    private void assignMvFromMvExpressions(GAPPMultivector mv, MvExpressions mvExpr) {
        Selectorset sel = new Selectorset();
        Variableset val = new Variableset();

        BooleanArr arr = getBooleanArr(mv);

        //assign Values from mv
        for (int blade = 0; blade < usedAlgebra.getBladeCount(); blade++) {
            if (mvExpr.bladeExpressions[blade] != null) {
                arr.setComponent(blade, true);
                sel.add(new Selector(blade,(byte) 1));

                DFGNodeType typeBlade = DFGNodeTypeGetter.getTypeOfDFGNode(mvExpr.bladeExpressions[blade]);

                if (typeBlade == DFGNodeType.FloatConstant) {
                    val.add(new GAPPConstant(((FloatConstant) mvExpr.bladeExpressions[blade]).getValue()));
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

    /**
     * Handles a Geometric Algebra Product and produces code to represent it in curGAPP
     * @param typeProduct The type of the Geometric Algebra Product, defined in de.gaalop.tba.cfgImport.DVGVisitorImport
     * @param node The node, which represents the product in DFG
     */
    private void handleGAProduct(Products typeProduct, BinaryOperation node) {
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
                    sel1.add(new Selector(curTriple.getLeftBlade(),(byte) 1));
                    sel2.add(new Selector(curTriple.getRightBlade(),curTriple.getSign()));
                }

                createCommandSeriesDotProd(destination, new Selector(blade,(byte) 1), mvL, mvR, sel1, sel2);
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
    private Triples[] getTriplesArr(Products typeProduct, BinaryOperation node) {

        MvExpressions mvExprL = expressions.get(node.getLeft());
        MvExpressions mvExprR = expressions.get(node.getRight());

        Triples[] triplesArr = new Triples[usedAlgebra.getBladeCount()];

        for (int bladeL = 0; bladeL < usedAlgebra.getBladeCount(); bladeL++) {
            if (mvExprL.bladeExpressions[bladeL] != null) {
                for (int bladeR = 0; bladeR < usedAlgebra.getBladeCount(); bladeR++) {
                    if (mvExprR.bladeExpressions[bladeR] != null) {

                        Multivector out;
                        switch (typeProduct) {
                            case INNER:
                                out = usedAlgebra.inner(bladeL, bladeR);
                                break;
                            case OUTER:
                                out = usedAlgebra.outer(bladeL, bladeR);
                                break;
                            case GEO:
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
    private void createCommandSeriesDotProd(GAPPMultivector destMv, Selector destSel, GAPPMultivector src1, GAPPMultivector src2, Selectorset sel1, Selectorset sel2) {
        GAPPVector v1 = new GAPPVector(createNewName(false));
        GAPPVector v2 = new GAPPVector(createNewName(false));
        curGAPP.addInstruction(new GAPPSetVector(v1, src1, sel1));
        curGAPP.addInstruction(new GAPPSetVector(v2, src2, sel2));
        curGAPP.addInstruction(new GAPPDotVectors(destMv, destSel, v1, v2));
    }

    /**
     * Creates a new GAPPCalculate instruction in GAPP member,
     * which represent the given UnaryCalculation
     * @param type The type of the GAPPCalculate
     * @param unaryExpr The UnaryOperation expression
     */
    private void doUnaryCalculation(CalculationType type, UnaryOperation unaryExpr) {
        Expression operand = unaryExpr.getOperand();

        operand.accept(this);

        GAPPMultivector mvO = getGAPPMultivectorFromExpression(operand);

        BooleanArr b1 = getBooleanArr(mvO);

        Selectorset sel = new Selectorset();

        for (int blade=0;blade<usedAlgebra.getBladeCount();blade++)
            if (b1.getComponent(blade))
            {
                sel.add(new Selector(blade, (byte) 1));
                getBooleanArr(destination).setComponent(blade, true);
            }

        curGAPP.addInstruction(new GAPPCalculateMv(type, destination, mvO, null, sel, null));

    }

    /**
     * Creates a new GAPPCalculate instruction in GAPP member,
     * which represent the given BinaryCalculation
     * @param type The type of the GAPPCalculate
     * @param binaryExpr The BinaryOperation expression
     */
    private void doBinaryCalculation(CalculationType type, BinaryOperation binaryExpr) {
        Expression left = binaryExpr.getLeft();
        Expression right = binaryExpr.getRight();

        left.accept(this);
        right.accept(this);

        GAPPMultivector mvL = getGAPPMultivectorFromExpression(left);
        GAPPMultivector mvR = getGAPPMultivectorFromExpression(right);

        BooleanArr b1 = getBooleanArr(mvL);
        BooleanArr b2 = getBooleanArr(mvR);

        Selectorset sel = new Selectorset();

        for (int blade=0;blade<usedAlgebra.getBladeCount();blade++)
            if (b1.getComponent(blade) || b2.getComponent(blade))
            {
                sel.add(new Selector(blade, (byte) 1));
                getBooleanArr(destination).setComponent(blade, true);
            }

        curGAPP.addInstruction(new GAPPCalculateMv(type, destination, mvL, mvR, sel, sel));

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
    
    @Override
    public void visit(Subtraction node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);

        GAPPMultivector mvLeft = getGAPPMultivectorFromExpression(node.getLeft());
        GAPPMultivector mvRight = getGAPPMultivectorFromExpression(node.getRight());

        Selectorset selSetL = getSelectorsetAllBladesPositiveAndAssigned(mvLeft);

        Selectorset selSetRp = getSelectorsetAllBladesPositiveAndAssigned(mvRight);
        Selectorset selSetRn = getSelectorsetAllBladesNegativeAndAssigned(mvRight);
        curGAPP.addInstruction(new GAPPResetMv(destination));
        curGAPP.addInstruction(new GAPPSetMv(destination, mvLeft, selSetL, selSetL));
        curGAPP.addInstruction(new GAPPAddMv(destination, mvRight, selSetRp, selSetRn));

        BooleanArr boolArrDest = getBooleanArr(destination);
        boolArrDest.or(getBooleanArr(mvLeft));
        boolArrDest.or(getBooleanArr(mvRight));
    }

    @Override
    public void visit(Addition node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);


        GAPPMultivector mvLeft = getGAPPMultivectorFromExpression(node.getLeft());
        GAPPMultivector mvRight = getGAPPMultivectorFromExpression(node.getRight());

        Selectorset selSetL = getSelectorsetAllBladesPositiveAndAssigned(mvLeft);
        Selectorset selSetR = getSelectorsetAllBladesPositiveAndAssigned(mvRight);

        curGAPP.addInstruction(new GAPPResetMv(destination));
        curGAPP.addInstruction(new GAPPSetMv(destination, mvLeft, selSetL, selSetL));
        curGAPP.addInstruction(new GAPPAddMv(destination, mvRight, selSetR, selSetR));

        BooleanArr boolArrDest = getBooleanArr(destination);
        boolArrDest.or(getBooleanArr(mvLeft));
        boolArrDest.or(getBooleanArr(mvRight));
    }

    @Override
    public void visit(Division node) {

        node.getLeft().accept(this);
        node.getRight().accept(this);

        GAPPMultivector mvL = getGAPPMultivectorFromExpression(node.getLeft());
        GAPPMultivector mvR = getGAPPMultivectorFromExpression(node.getRight());

        BooleanArr b1 = getBooleanArr(mvL);

        Selectorset sel1 = new Selectorset();
        Selectorset sel2 = new Selectorset();

        for (int blade=0;blade<usedAlgebra.getBladeCount();blade++)
            if (b1.getComponent(blade))
            {
                sel1.add(new Selector(blade,(byte) 1));
                sel2.add(new Selector(0,(byte) 1));
                getBooleanArr(destination).setComponent(blade, true);
            }
        
        curGAPP.addInstruction(new GAPPCalculateMv(CalculationType.DIVISION, destination, mvL, mvR, sel1,sel2));
    }

    @Override
    public void visit(InnerProduct node) {
        handleGAProduct(Products.INNER, node);
    }

    @Override
    public void visit(OuterProduct node) {
        handleGAProduct(Products.OUTER, node);
    }

    @Override
    public void visit(Multiplication node) {
        handleGAProduct(Products.GEO, node);
    }

    @Override
    public void visit(MathFunctionCall node) {
        node.getOperand().accept(this);

        GAPPMultivector mvOp = getGAPPMultivectorFromExpression(node.getOperand());

        if (getBooleanArr(mvOp).getComponent(0)) {

            Selectorset sel = new Selectorset();
            sel.add(new Selector(0, (byte) 1));
            getBooleanArr(destination).setComponent(0, true);
            curGAPP.addInstruction(new GAPPCalculateMv(CalculationType.valueOf(node.getFunction().name()), destination, mvOp, null, sel, null));

        } else {
            System.out.println(node.getFunction()+":"+node.getOperand());
        }
        System.err.println("Warning: MathFunctionCall is only implemented for scalars!");
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
        node.getLeft().accept(this);
        node.getRight().accept(this);

        GAPPMultivector mvL = getGAPPMultivectorFromExpression(node.getLeft());
        GAPPMultivector mvR = getGAPPMultivectorFromExpression(node.getRight());

        Selectorset sel = new Selectorset();
        sel.add(new Selector(0, (byte) 1));
        getBooleanArr(destination).setComponent(0, true);
        curGAPP.addInstruction(new GAPPCalculateMv(CalculationType.EXPONENTIATION, destination, mvL, mvR, sel, sel));
        System.err.println("Warning: Exponentiation is only implemented for scalars!");
    }

    @Override
    public void visit(FloatConstant node) {
    }

    @Override
    public void visit(BaseVector node) {
    }

    @Override
    public void visit(Negation node) {
        node.getOperand().accept(this);

        GAPPMultivector mvRight = getGAPPMultivectorFromExpression(node.getOperand());

        Selectorset selSetRp = getSelectorsetAllBladesPositiveAndAssigned(mvRight);
        Selectorset selSetRn = getSelectorsetAllBladesNegativeAndAssigned(mvRight);
        curGAPP.addInstruction(new GAPPResetMv(destination));
        curGAPP.addInstruction(new GAPPAddMv(destination, mvRight, selSetRp, selSetRn));

        BooleanArr boolArrDest = getBooleanArr(destination);
        boolArrDest.or(getBooleanArr(mvRight));
    }

    @Override
    public void visit(Reverse node) {
        node.getOperand().accept(this);

        GAPPMultivector mvOp = getGAPPMultivectorFromExpression(node.getOperand());

        Selectorset selOp = new Selectorset();
        Selectorset selDest = new Selectorset();

        BooleanArr booleanArr = getBooleanArr(mvOp);

        for (int blade = 0;blade<usedAlgebra.getBladeCount();blade++)
            if (booleanArr.getComponent(blade))
            {
                selDest.add(new Selector(blade, (byte) 1));
                int k = usedAlgebra.getGrade(blade);
                if (((k*(k-1))/2) % 2 == 0)
                        selOp.add(new Selector(blade, (byte) 1));
                else
                        selOp.add(new Selector(blade, (byte) -1));
            }

        getBooleanArr(destination).or(booleanArr);

        curGAPP.addInstruction(new GAPPResetMv(destination));
        curGAPP.addInstruction(new GAPPSetMv(destination,mvOp,selDest,selOp));
    }

    

    @Override
    public void visit(LogicalOr node) {
        doBinaryCalculation(CalculationType.LOG_OR, node);
    }

    @Override
    public void visit(LogicalAnd node) {
        doBinaryCalculation(CalculationType.LOG_AND, node);
    }

    @Override
    public void visit(LogicalNegation node) {
        doUnaryCalculation(CalculationType.LOG_INV, node);
    }

    @Override
    public void visit(Equality node) {
        doBinaryCalculation(CalculationType.EQUAL, node);
    }

    @Override
    public void visit(Inequality node) {
        doBinaryCalculation(CalculationType.INEQUAL, node);
    }

    @Override
    public void visit(Relation relation) {

        CalculationType type = null;
        switch (relation.getType()) {
            case GREATER:
                type = CalculationType.REL_GR;
                break;
            case GREATER_OR_EQUAL:
                type = CalculationType.REL_GREQ;
                break;
            case LESS:
                type = CalculationType.REL_LE;
                break;
            case LESS_OR_EQUAL:
                type = CalculationType.REL_LEEQ;
                break;
            default:
                System.err.println("Unknown equivalent GAPP Calculation to "+relation.getType().name());
        }

        doBinaryCalculation(type, relation);
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
