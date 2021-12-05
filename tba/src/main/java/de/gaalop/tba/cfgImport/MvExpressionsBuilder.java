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
import java.util.Map.Entry;
import java.util.TreeMap;

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
    private boolean scalarFunctions;
    private Variable curVariable;
    private AlgebraDefinitionFile alFile;

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
        return new MvExpressions(counterMv + "", bladeCount);
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
        
        for (Entry<Integer, Expression> l: left.bladeExpressions.entrySet()) {
            for (Entry<Integer, Expression> r: right.bladeExpressions.entrySet()) {
            
                Expression prodExpr = new Multiplication(l.getValue(), r.getValue());
                Multivector prodMv = usedAlgebra.getProduct(typeProduct, l.getKey(), r.getKey());

                TreeMap<Integer, Byte> prod = prodMv.getValueArr(algebra);

                for (Entry<Integer, Byte> blade: prod.entrySet()) {
                    Expression prodExpri = new Multiplication(prodExpr, new FloatConstant(blade.getValue()));
                        
                    if (result.bladeExpressions.containsKey(blade.getKey())) {
                        result.setExpression(blade.getKey(), new Addition(result.bladeExpressions.get(blade.getKey()), prodExpri));
                    } else {
                        set = true; 
                        result.setExpression(blade.getKey(), prodExpri);
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
        result.bladeExpressions.putAll(left.bladeExpressions);
        
        right.bladeExpressions.entrySet().forEach(r -> { 
            result.bladeExpressions.put(r.getKey(), (result.bladeExpressions.containsKey(r.getKey()))
                    ? new Subtraction(result.bladeExpressions.get(r.getKey()), r.getValue())
                    : new Negation(r.getValue())
            );
        });

        expressions.put(node, result);
    }

    @Override
    public void visit(Addition node) {
        traverseBinary(node);
        MvExpressions left = expressions.get(node.getLeft());
        MvExpressions right = expressions.get(node.getRight());

        MvExpressions result = createNewMvExpressions();
        result.bladeExpressions.putAll(left.bladeExpressions);
        right.bladeExpressions.forEach(
            (bladeIndex, expr) -> result.bladeExpressions.merge(bladeIndex, expr, (vL, vR) -> new Addition(vL, vR))
        );
        expressions.put(node, result);
    }

    /**
     * Returns the reverse of a MvExpressions instance
     * @param mv The MvExpressions instance to be reversed
     * @return The reverse
     */
    private MvExpressions getReverse(MvExpressions mv) {
        MvExpressions result = createNewMvExpressions();
        
        mv.bladeExpressions.entrySet().forEach(blade -> {
            int k = usedAlgebra.getGrade(blade.getKey());
            if (((k * (k - 1)) / 2) % 2 == 0) {
                result.bladeExpressions.put(blade.getKey(), blade.getValue());
            }
            else {
                result.bladeExpressions.put(blade.getKey(), new Negation(blade.getValue()));
            }
        });

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
        Expression lengthScalar = length.bladeExpressions.get(0);

        MvExpressions result = createNewMvExpressions();
        revR.bladeExpressions.entrySet().forEach(blade -> {
            result.bladeExpressions.put(blade.getKey(), new Division(blade.getValue().copy(), lengthScalar));
        });

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

        MvExpressions result = createNewMvExpressions();
        String key = node.toString();
        if (variables.containsKey(key)) {
            MvExpressions variableValue = variables.get(key);
            String varName = node.getName();
            variableValue.bladeExpressions.entrySet().forEach(blade -> {
                result.bladeExpressions.put(blade.getKey(), new MultivectorComponent(varName, blade.getKey()));
            });
        } else {
            //input variable!
            result.setExpression(0, node);
        }

        expressions.put(node, result);
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
        
        op.bladeExpressions.entrySet().forEach(blade -> {
            result.bladeExpressions.put(blade.getKey(), new Negation(blade.getValue()));
        });
        
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
            
            for (Entry<Integer, Expression> blade: r.bladeExpressions.entrySet()) {
                if (l.bladeExpressions.containsKey(blade.getKey())) {
                    Expression summand = new Multiplication(l.bladeExpressions.get(blade.getKey()), blade.getValue());
                    sum = (sum == null) ? summand : new Addition(sum, summand);
                }
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
