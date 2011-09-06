package de.gaalop.tba.cfgImport;

import de.gaalop.cfg.StartNode;
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
import de.gaalop.tba.Multivector;
import de.gaalop.tba.Products;
import de.gaalop.tba.UseAlgebra;

/**
 * Build the mvExpressions for every variable and expression
 * @author christian
 */
public class MvExpressionsBuilder extends EmptyControlFlowVisitor implements ExpressionVisitor {

	public HashMap<String,MvExpressions> variables;
        public HashMap<Expression,MvExpressions> expressions;

	private int counterMv;
	public int bladeCount;

        private UseAlgebra usedAlgebra;

	private final double EPSILON = 10E-07;

//	public static final byte INNER = 0;
//	public static final byte OUTER = 1;
//	public static final byte GEO = 2;

	public MvExpressionsBuilder(UseAlgebra usedAlgebra) {
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
         *
         * @param node The current assignment node
         * @param mvExpr The MvExpressions for the value
         * @param variable The destination variable
         * @return The node to proceed on with the visit
         */
        protected AssignmentNode changeGraph(AssignmentNode node,MvExpressions mvExpr,Variable variable) {
            return node;
        }

	@Override
	public void visit(AssignmentNode node) {
		Variable variable = node.getVariable();
		Expression value = node.getValue();

		value.accept(this);

		MvExpressions mvExpr = expressions.get(value);
		variables.put(variable.toString(), mvExpr);

                // when GAPP performing, only the MvExpressions are needed,
                // the graph itself mustn't changed, espescially nodes won't be inserted.
                // That's why we use a method for changing graph
                AssignmentNode lastNode = changeGraph(node,mvExpr,variable);

                
		lastNode.getSuccessor().accept(this);
	}

        /**
         * Creates a new MvExpressions instance
         * @return The new MvExpressions
         */
	private MvExpressions createNewMvExpressions() {
		counterMv++;
		return new MvExpressions(counterMv+"",bladeCount);
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

		for (int bladeL = 0;bladeL<bladeCount;bladeL++)
			if (left.bladeExpressions[bladeL] != null)
			{
				for (int bladeR = 0;bladeR<bladeCount;bladeR++)
					if (right.bladeExpressions[bladeR] != null)
					{
						Expression prodExpr = new Multiplication(left.bladeExpressions[bladeL],right.bladeExpressions[bladeR]);
						Multivector prodMv = usedAlgebra.getProduct(typeProduct,bladeL,bladeR);

						double[] prod = prodMv.getValueArr();

						for (int bladeResult = 0;bladeResult<bladeCount;bladeResult++)
							if (Math.abs(prod[bladeResult])>EPSILON) {
								Expression prodExpri = new Multiplication(prodExpr,new FloatConstant((float) prod[bladeResult]));
								if (result.bladeExpressions[bladeResult] == null)
									result.bladeExpressions[bladeResult] = prodExpri;
								else
									result.bladeExpressions[bladeResult] = new Addition(result.bladeExpressions[bladeResult],prodExpri);
							}
					}
			}
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
		for (int blade=0;blade<bladeCount;blade++) {

			if (left.bladeExpressions[blade] != null) {
				if (right.bladeExpressions[blade] != null)
					result.bladeExpressions[blade] = new Subtraction(left.bladeExpressions[blade],right.bladeExpressions[blade]);
				else
					result.bladeExpressions[blade] = left.bladeExpressions[blade];

			} else
				if (right.bladeExpressions[blade] != null)
					result.bladeExpressions[blade] = new Negation(right.bladeExpressions[blade]);

		}

		expressions.put(node, result);
	}

	@Override
	public void visit(Addition node) {
		traverseBinary(node);
		MvExpressions left = expressions.get(node.getLeft());
		MvExpressions right = expressions.get(node.getRight());

		MvExpressions result = createNewMvExpressions();
		for (int blade=0;blade<bladeCount;blade++) {

			if (left.bladeExpressions[blade] != null) {
				if (right.bladeExpressions[blade] != null)
					result.bladeExpressions[blade] = new Addition(left.bladeExpressions[blade],right.bladeExpressions[blade]);
				else
					result.bladeExpressions[blade] = left.bladeExpressions[blade];

			} else
				if (right.bladeExpressions[blade] != null)
					result.bladeExpressions[blade] = right.bladeExpressions[blade];

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

		for (int blade=0;blade<bladeCount;blade++)
                    if (mv.bladeExpressions[blade] != null) {
                            int k = usedAlgebra.getGrade(blade);
                            if (((k*(k-1))/2) % 2 == 0)
                                    result.bladeExpressions[blade] = mv.bladeExpressions[blade];
                            else
                                    result.bladeExpressions[blade] = new Negation(mv.bladeExpressions[blade]);
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

             for (int blade=0;blade<bladeCount;blade++)
                 if (mv.bladeExpressions[blade] != null)
                    result.bladeExpressions[blade] = new Division(mv.bladeExpressions[blade],length.bladeExpressions[0]);

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
		traverseUnary(node);



		MvExpressions result = createNewMvExpressions();
		switch (node.getFunction()) {
		case ABS:
			//sqrt(abs(op*op))

                        MvExpressions op = expressions.get(node.getOperand());
                        MvExpressions opR = getReverse(op);
                        MvExpressions prod = calculateUsingMultTable(Products.GEO, op, opR);

			Expression i0 = prod.bladeExpressions[0];

			if (i0 == null) i0 = new FloatConstant(0);

			result.bladeExpressions[0] = new MathFunctionCall(new MathFunctionCall(i0,MathFunction.ABS),MathFunction.SQRT);

			break;
		case SQRT:
			//sqrt(scalar)
			result.bladeExpressions[0] = new MathFunctionCall(expressions.get(node.getOperand()).bladeExpressions[0],MathFunction.SQRT);
			break;
		default:
			result.bladeExpressions[0] = new MathFunctionCall(expressions.get(node.getOperand()).bladeExpressions[0],node.getFunction());
                        System.err.println("Warning: "+node.getFunction().toString()+" is only implemented for scalar inputs!");
			break;
		}

		expressions.put(node,result);
	}

	@Override
	public void visit(Variable node) {

		MvExpressions v = null;
		String key = node.toString();
		if (variables.containsKey(key)) {
			v = createNewMvExpressions();

			for (int i=0;i<bladeCount;i++)
				if (variables.get(key).bladeExpressions[i] != null)
					v.bladeExpressions[i] = new MultivectorComponent(node.getName(),i);

		} else {
			//input variable!
			v = createNewMvExpressions();
			v.bladeExpressions[0] = node; // TODO Varibale removal
		}

		expressions.put(node,v);
	}

	@Override
	public void visit(MultivectorComponent node) {
		expressions.put(node,variables.get(node.toString()));
	}



	@Override
	public void visit(FloatConstant node) {
		MvExpressions result = createNewMvExpressions();
		result.bladeExpressions[0] = node;
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
		result.bladeExpressions[node.getOrder()] = new FloatConstant(1);
		expressions.put(node,result);
	}

	@Override
	public void visit(Negation node) {
		traverseUnary(node);
		MvExpressions op = expressions.get(node.getOperand());

		MvExpressions result = createNewMvExpressions();

		for (int blade=0;blade<bladeCount;blade++)
			if (op.bladeExpressions[blade] != null)
				result.bladeExpressions[blade] = new Negation(op.bladeExpressions[blade]);


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

                result.bladeExpressions[0] = new LogicalOr(l.bladeExpressions[0], r.bladeExpressions[0]);

		expressions.put(node, result);

		System.err.println("Warning: LogicalOr is only implemented for scalars!");
	}

	@Override
	public void visit(LogicalAnd node) {
		traverseBinary(node);
		MvExpressions l = expressions.get(node.getLeft());
                MvExpressions r = expressions.get(node.getRight());

                MvExpressions result = createNewMvExpressions();

                result.bladeExpressions[0] = new LogicalAnd(l.bladeExpressions[0], r.bladeExpressions[0]);

		expressions.put(node, result);

		System.err.println("Warning: LogicalAnd is only implemented for scalars!");
	}

	@Override
	public void visit(LogicalNegation node) {
                traverseUnary(node);
		MvExpressions op = expressions.get(node.getOperand());

                MvExpressions result = createNewMvExpressions();

                result.bladeExpressions[0] = new LogicalNegation(op.bladeExpressions[0]);

		expressions.put(node, result);

		System.err.println("Warning: LogicalNegation is only implemented for scalars!");
	}

	@Override
	public void visit(Equality node) {
		traverseBinary(node);
		MvExpressions l = expressions.get(node.getLeft());
                MvExpressions r = expressions.get(node.getRight());

                MvExpressions result = createNewMvExpressions();

                result.bladeExpressions[0] = new Equality(l.bladeExpressions[0], r.bladeExpressions[0]);

		expressions.put(node, result);

		System.err.println("Warning: Equality is only implemented for scalars!");
	}

	@Override
	public void visit(Inequality node) {
		traverseBinary(node);
		MvExpressions l = expressions.get(node.getLeft());
                MvExpressions r = expressions.get(node.getRight());

                MvExpressions result = createNewMvExpressions();

                result.bladeExpressions[0] = new Inequality(l.bladeExpressions[0], r.bladeExpressions[0]);

		expressions.put(node, result);

		System.err.println("Warning: Inequality is only implemented for scalars!");
	}

	@Override
	public void visit(Relation node) {
		traverseBinary(node);
		MvExpressions l = expressions.get(node.getLeft());
                MvExpressions r = expressions.get(node.getRight());

                MvExpressions result = createNewMvExpressions();

                result.bladeExpressions[0] = new Relation(l.bladeExpressions[0], r.bladeExpressions[0],node.getType());

		expressions.put(node, result);

		System.err.println("Warning: Relation is only implemented for scalars!");
	}

        @Override
	public void visit(Exponentiation node) {
		traverseBinary(node);
		MvExpressions l = expressions.get(node.getLeft());
                MvExpressions r = expressions.get(node.getRight());

                MvExpressions result = createNewMvExpressions();

                result.bladeExpressions[0] = new Exponentiation(l.bladeExpressions[0], r.bladeExpressions[0]);

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

        @Override
        public void visit(StartNode node) {
            node.getGraph().setSignature(usedAlgebra.getAlgebraSignature());
            super.visit(node);
        }



}
