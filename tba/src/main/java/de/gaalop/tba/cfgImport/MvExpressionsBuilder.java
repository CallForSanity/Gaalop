package de.gaalop.tba.cfgImport;

import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.dfg.ExpressionVisitor;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import java.util.HashMap;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.Equality;
import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
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
import de.gaalop.dfg.UnaryOperation;
import de.gaalop.dfg.Variable;
import de.gaalop.tba.Algebra;
import de.gaalop.tba.Multivector;
import de.gaalop.tba.Products;
import de.gaalop.tba.UseAlgebra;

/**
 * Build the mvExpressions for every variable and expression
 * @author Christian Steinmetz
 */
public class MvExpressionsBuilder extends EmptyControlFlowVisitor implements ExpressionVisitor {

    public HashMap<String, MvExpressions> variables;
    public HashMap<Expression, MvExpressions> expressions;
    private int counterMv;
    public int bladeCount;
    private UseAlgebra usedAlgebra;
    private final double EPSILON = 10E-07;
    private boolean scalarFunctions;
    private Variable curVariable;
    private AlgebraDefinitionFile alFile;

    public boolean useSparseExpressions = false;

    public MvExpressionsBuilder(UseAlgebra usedAlgebra, boolean scalarFunctions, AlgebraDefinitionFile alFile) {
        this.scalarFunctions = scalarFunctions;
        this.alFile = alFile;
        variables = new HashMap<String, MvExpressions>();
        this.usedAlgebra = usedAlgebra;
        counterMv = 0;

        bladeCount = usedAlgebra.getBladeCount();

        expressions = new HashMap<Expression, MvExpressions>();
    }

    /**
     * This method is called in Assignment node visit.
     * Here is the place to change the graph.
     *
     * In the MvExpresseionsBuilder class, the graph isn't changed.
     *
     * @param node The current assignment node
     * @param mvExpr The MvExpressions for the value
     * @param variable The destination variable
     * @return The node to proceed on with the visit
     */
    protected AssignmentNode changeGraph(AssignmentNode node, MvExpressions mvExpr, Variable variable) {
        return node;
    }

    @Override
    public void visit(AssignmentNode node) {
        curVariable = node.getVariable();

        Expression value = node.getValue();


        value.accept(this);

        MvExpressions mvExpr = expressions.get(value);
        variables.put(curVariable.toString(), mvExpr);

        // when GAPP performing, only the MvExpressions are needed,
        // the graph itself mustn't changed, espescially nodes won't be inserted.
        // That's why we use a method for changing graph
        AssignmentNode lastNode = node;
        if (scalarFunctions) {
            lastNode = changeGraph(node, mvExpr, curVariable);
        } else {
            if (!(value instanceof MathFunctionCall)) {
                lastNode = changeGraph(node, mvExpr, curVariable);
            }
        }


        lastNode.getSuccessor().accept(this);
    }

    /**
     * Creates a new MvExpressions instance
     * @return The new MvExpressions
     */
    private MvExpressions createNewMvExpressions() {
        counterMv++;
        if(this.useSparseExpressions) {
            return new SparseMvExpressions(counterMv + "", bladeCount);
        } else {
            return new DenseMvExpressions(counterMv + "", bladeCount);
        }
    }

    /**
     * Calculates the product of two MvExpressions
     * @param typeProduct The type of the product
     * @param left The first factor
     * @param right The second factor
     * @return The product
     */
    private MvExpressions calculateUsingMultTable(Products typeProduct, MvExpressions left, MvExpressions right) {
        MvExpressions result = createNewMvExpressions();
        Algebra algebra = usedAlgebra.getAlgebra();
        boolean set = false;
        for (int bladeL = 0; bladeL < bladeCount; bladeL++) {
            if (left.getExpression(bladeL) != null) {
                for (int bladeR = 0; bladeR < bladeCount; bladeR++) {
                    if (right.getExpression(bladeR) != null) {
                        Expression prodExpr = new Multiplication(left.getExpression(bladeL), right.getExpression(bladeR));
                        Multivector prodMv = usedAlgebra.getProduct(typeProduct, bladeL, bladeR);

                        byte[] prod = prodMv.getValueArr(algebra);

                        for (int bladeResult = 0; bladeResult < bladeCount; bladeResult++) {
                            if (Math.abs(prod[bladeResult]) > EPSILON) {
                                Expression prodExpri = new Multiplication(prodExpr, new FloatConstant(prod[bladeResult]));
                                if (result.getExpression(bladeResult) == null) {
                                    set = true; 
                                   result.setExpression(bladeResult, prodExpri);
                                } else {
                                    result.setExpression(bladeResult, new Addition(result.getExpression(bladeResult), prodExpri));
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!set) 
            result.setExpression(0, new FloatConstant(0)); //Without this, e.g. ?r = sqrt(a.b); fails.
        return result;
    }

    /**
     * Dummy method for calculating a product
     * @param typeProduct The type of the product
     * @param node The node which represents the product to be calculated
     */
    private void calculateUsingMultTable(Products typeProduct, BinaryOperation node) {
        MvExpressions left = expressions.get(node.getLeft());
        MvExpressions right = expressions.get(node.getRight());

        MvExpressions result = calculateUsingMultTable(typeProduct, left, right);

        expressions.put(node, result);
    }

    /**
     * Dummy method for traversing a binary operation node
     * @param node The binary operation node
     */
    private void traverseBinary(BinaryOperation node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    /**
     * Dummy method for traversing a unary operation node
     * @param node The unary operation node
     */
    private void traverseUnary(UnaryOperation node) {
        node.getOperand().accept(this);
    }

    @Override
    public void visit(Subtraction node) {
        traverseBinary(node);
        MvExpressions left = expressions.get(node.getLeft());
        MvExpressions right = expressions.get(node.getRight());

        MvExpressions result = createNewMvExpressions();
        for (int blade = 0; blade < bladeCount; blade++) {

            if (left.getExpression(blade) != null) {
                if (right.getExpression(blade) != null) {
                    result.setExpression(blade, new Subtraction(left.getExpression(blade), right.getExpression(blade)));
                } else {
                    result.setExpression(blade, left.getExpression(blade));
                }

            } else if (right.getExpression(blade) != null) {
                result.setExpression(blade, new Negation(right.getExpression(blade)));
            }

        }

        expressions.put(node, result);
    }

    @Override
    public void visit(Addition node) {
        traverseBinary(node);
        MvExpressions left = expressions.get(node.getLeft());
        MvExpressions right = expressions.get(node.getRight());

        MvExpressions result = createNewMvExpressions();
        for (int blade = 0; blade < bladeCount; blade++) {

            if (left.getExpression(blade) != null) {
                if (right.getExpression(blade) != null) {
                    result.setExpression(blade, new Addition(left.getExpression(blade), right.getExpression(blade)));
                } else {
                    result.setExpression(blade, left.getExpression(blade));
                }

            } else if (right.getExpression(blade) != null) {
                result.setExpression(blade, right.getExpression(blade));
            }

        }

        expressions.put(node, result);

    }

    /**
     * Returns the reverse of a MvExpressions instance
     * @param mv The MvExpressions instance to be reversed
     * @return The reverse
     */
    private MvExpressions getReverse(MvExpressions mv) {
        MvExpressions result = createNewMvExpressions();

        for (int blade = 0; blade < bladeCount; blade++) {
            if (mv.getExpression(blade) != null) {
                int k = usedAlgebra.getGrade(blade);
                if (((k * (k - 1)) / 2) % 2 == 0) {
                    result.setExpression(blade, mv.getExpression(blade));
                } else {
                    result.setExpression(blade, new Negation(mv.getExpression(blade)));
                }
            }
        }
        return result;
    }

    /**
     * Returns the inverse of a MvExpressions object
     * @param mv The MvExpressions object to be inversed
     * @return The inverse
     */
    private MvExpressions getInverse(MvExpressions mv) {
        MvExpressions revR = getReverse(mv);
        MvExpressions length = calculateUsingMultTable(Products.GEO, mv, revR);

        MvExpressions result = createNewMvExpressions();

        for (int blade = 0; blade < bladeCount; blade++) {
            if (mv.getExpression(blade) != null) {
                result.setExpression(blade, new Division(revR.getExpression(blade).copy(), length.getExpression(0)));
            }
        }

        return result;
    }

    @Override
    public void visit(Division node) {
        traverseBinary(node);

        MvExpressions l = expressions.get(node.getLeft());
        MvExpressions r = expressions.get(node.getRight());

        MvExpressions inverse = getInverse(r);

        MvExpressions result = calculateUsingMultTable(Products.GEO, l, inverse);

        expressions.put(node, result);

    }

    @Override
    public void visit(InnerProduct node) {
        traverseBinary(node);
        calculateUsingMultTable(Products.INNER, node);
    }

    @Override
    public void visit(Multiplication node) {
        traverseBinary(node);
        calculateUsingMultTable(Products.GEO, node);
    }

    @Override
    public void visit(MathFunctionCall node) {
        MvExpressions result = createNewMvExpressions();
        if (scalarFunctions) {

            traverseUnary(node);


            switch (node.getFunction()) {
                case ABS:
                    MvExpressions op = expressions.get(node.getOperand());
                    
                    // if the operand is only a scalar, then there is a shorthand: result = abs(operand);
                    if (op.getExpression(0) != null) {
                        boolean allOthersAreNull = true;
                        boolean firstElement = true;
                        for (Expression expr: op.getAllExpressions()) {
                            if (firstElement) {
                                firstElement = false;
                            } else 
                                if (expr != null)
                                    allOthersAreNull = false;
                        }
                        
                        if (allOthersAreNull) {
                            // scalar not null, all other are null
                            result.setExpression(0, new MathFunctionCall(op.getExpression(0), MathFunction.ABS));
                            break;
                        }
                    }
                    
                    //Use the ordinary method result = sqrt(abs(op.(~op)))
                    MvExpressions opR = getReverse(op);
                    MvExpressions prod = calculateUsingMultTable(Products.GEO, op, opR);

                    Expression i0 = prod.getExpression(0);

                    if (i0 == null) {
                        i0 = new FloatConstant(0);
                    }

                    result.setExpression(0, new MathFunctionCall(new MathFunctionCall(i0, MathFunction.ABS), MathFunction.SQRT));

                    break;
                case SQRT:
                    //sqrt(scalar)
                    result.setExpression(0, new MathFunctionCall(expressions.get(node.getOperand()).getExpression(0), MathFunction.SQRT));
                    break;
                default:
                    result.setExpression(0, new MathFunctionCall(expressions.get(node.getOperand()).getExpression(0), node.getFunction()));
                    System.err.println("Warning: " + node.getFunction().toString() + " is only implemented for scalar inputs!");
                    break;
            }

        } else {
            for (int blade = 0; blade < bladeCount; blade++) {
                result.setExpression(blade, new MultivectorComponent(curVariable.getName(), blade));
            }
        }

        expressions.put(node, result);
    }

    @Override
    public void visit(Variable node) {

        MvExpressions v = null;
        String key = node.toString();
        if (variables.containsKey(key)) {
            v = createNewMvExpressions();

            for (int i = 0; i < bladeCount; i++) {
                if (variables.get(key).getExpression(i) != null) {
                    v.setExpression(i, new MultivectorComponent(node.getName(), i));
                }
            }

        } else {
            //input variable!
            v = createNewMvExpressions();
            v.setExpression(0, node);
        }

        expressions.put(node, v);
    }

    @Override
    public void visit(MultivectorComponent node) {
        expressions.put(node, variables.get(node.toString()));
    }

    @Override
    public void visit(FloatConstant node) {
        MvExpressions result = createNewMvExpressions();
        result.setExpression(0, node);
        expressions.put(node, result);
    }

    @Override
    public void visit(OuterProduct node) {
        traverseBinary(node);
        calculateUsingMultTable(Products.OUTER, node);
    }

    @Override
    public void visit(BaseVector node) {
        MvExpressions result = createNewMvExpressions();
        result.setExpression(alFile.getIndex(node.toString()), new FloatConstant(1));
        expressions.put(node, result);
    }

    @Override
    public void visit(Negation node) {
        traverseUnary(node);
        MvExpressions op = expressions.get(node.getOperand());

        MvExpressions result = createNewMvExpressions();

        for (int blade = 0; blade < bladeCount; blade++) {
            if (op.getExpression(blade) != null) {
                result.setExpression(blade, new Negation(op.getExpression(blade)));
            }
        }


        expressions.put(node, result);
    }

    @Override
    public void visit(Reverse node) {
        traverseUnary(node);

        MvExpressions op = expressions.get(node.getOperand());

        MvExpressions result = getReverse(op);

        expressions.put(node, result);

    }

    @Override
    public void visit(LogicalOr node) {
        traverseBinary(node);
        MvExpressions l = expressions.get(node.getLeft());
        MvExpressions r = expressions.get(node.getRight());

        MvExpressions result = createNewMvExpressions();

        result.setExpression(0, new LogicalOr(l.getExpression(0), r.getExpression(0)));

        expressions.put(node, result);

        System.err.println("Warning: LogicalOr is only implemented for scalars!");
    }

    @Override
    public void visit(LogicalAnd node) {
        traverseBinary(node);
        MvExpressions l = expressions.get(node.getLeft());
        MvExpressions r = expressions.get(node.getRight());

        MvExpressions result = createNewMvExpressions();

        result.setExpression(0, new LogicalAnd(l.getExpression(0), r.getExpression(0)));

        expressions.put(node, result);

        System.err.println("Warning: LogicalAnd is only implemented for scalars!");
    }

    @Override
    public void visit(LogicalNegation node) {
        traverseUnary(node);
        MvExpressions op = expressions.get(node.getOperand());

        MvExpressions result = createNewMvExpressions();

        result.setExpression(0, new LogicalNegation(op.getExpression(0)));

        expressions.put(node, result);

        System.err.println("Warning: LogicalNegation is only implemented for scalars!");
    }

    @Override
    public void visit(Equality node) {
        traverseBinary(node);
        MvExpressions l = expressions.get(node.getLeft());
        MvExpressions r = expressions.get(node.getRight());

        MvExpressions result = createNewMvExpressions();

        result.setExpression(0, new Equality(l.getExpression(0), r.getExpression(0)));

        expressions.put(node, result);

        System.err.println("Warning: Equality is only implemented for scalars!");
    }

    @Override
    public void visit(Inequality node) {
        traverseBinary(node);
        MvExpressions l = expressions.get(node.getLeft());
        MvExpressions r = expressions.get(node.getRight());

        MvExpressions result = createNewMvExpressions();

        result.setExpression(0, new Inequality(l.getExpression(0), r.getExpression(0)));

        expressions.put(node, result);

        System.err.println("Warning: Inequality is only implemented for scalars!");
    }

    @Override
    public void visit(Relation node) {
        traverseBinary(node);
        MvExpressions l = expressions.get(node.getLeft());
        MvExpressions r = expressions.get(node.getRight());

        MvExpressions result = createNewMvExpressions();
        
        if (node.getType() == Relation.Type.COEFFICIENT) {
            // Coefficient equals to the dot product of the two arrays l and r
            Expression sum = null;
            
            for (int blade = 0; blade < bladeCount; blade++) 
                if (l.getExpression(blade) != null && r.getExpression(blade) != null) {
                    Expression summand = new Multiplication(l.getExpression(blade), r.getExpression(blade));
                    sum = (sum == null) ? summand : new Addition(sum, summand);
                }
            
            if (sum != null) 
                result.setExpression(0, sum);
            
        } else {
            result.setExpression(0, new Relation(l.getExpression(0), r.getExpression(0), node.getType()));
            System.err.println("Warning: Relation is only implemented for scalars!");
        }

        expressions.put(node, result);   
    }

    @Override
    public void visit(Exponentiation node) {
        traverseBinary(node);
        MvExpressions l = expressions.get(node.getLeft());
        MvExpressions r = expressions.get(node.getRight());

        MvExpressions result = createNewMvExpressions();

        result.setExpression(0, new Exponentiation(l.getExpression(0), r.getExpression(0)));

        expressions.put(node, result);

        System.err.println("Warning: Exponentiation is only implemented for scalars!");
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("Macros should have been inlined and no function arguments should be the graph.");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("Macros should have been inlined and no macro calls should be in the graph.");
    }

}
