package de.gaalop.gappImporting.cfgimport;

import java.util.HashMap;

import de.gaalop.tba.Multivector;

import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.EmptyExpressionVisitor;
import de.gaalop.dfg.Equality;
import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionFactory;
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
import de.gaalop.dfg.Variable;

public class CFGExpressionVisitor extends EmptyExpressionVisitor {
//implements ExpressionVisitor {
	
	private Variable currentOutputVariable;
	
	public HashMap<Expression,MvExpressions> expressions;
	public HashMap<String,MvExpressions> variables;
	
	private int counterMv;
	public int bladeCount;
	private UseAlgebra usedAlgebra;
	private final double EPSILON = 10E-07;
	
	private final byte INNER = 0;
	private final byte OUTER = 1;
	private final byte GEO = 2;
	
	public CFGExpressionVisitor() {
		counterMv = 0;
		
		//load 5d conformal algebra
		usedAlgebra = new UseAlgebra();
		usedAlgebra.load5dAlgebra();
		bladeCount = 32;
		
		expressions = new HashMap<Expression, MvExpressions>();
	}

	public Variable getCurrentOutputVariable() {
		return currentOutputVariable;
	}

	public void setCurrentOutputVariable(Variable currentOutputVariable) {
		this.currentOutputVariable = currentOutputVariable;
	}

	private MvExpressions createNewMvExpressions() {
		counterMv++;
		return new MvExpressions(counterMv+"",bladeCount);
	}
	
	private void calculateUsingMultTable(byte typeProduct,BinaryOperation node) {
		MvExpressions left = expressions.get(node.getLeft());
		MvExpressions right = expressions.get(node.getRight());
		
		MvExpressions result = createNewMvExpressions();
		
		for (int bladeL = 0;bladeL<bladeCount;bladeL++) 
			if (left.bladeExpressions[bladeL] != null)
			{
				for (int bladeR = 0;bladeR<bladeCount;bladeR++)
					if (right.bladeExpressions[bladeR] != null)
					{
						Expression prodExpr = new Multiplication(left.bladeExpressions[bladeL],right.bladeExpressions[bladeR]);
						
						Multivector prodMv = null;
						switch (typeProduct) {
						case INNER:
							prodMv = usedAlgebra.inner(bladeL, bladeR);
							break;
						case OUTER:
							prodMv = usedAlgebra.outer(bladeL, bladeR);
							break;
						case GEO:
							prodMv = usedAlgebra.geo(bladeL, bladeR);
							break;
						default:
							System.err.println("Product type is unknown!");
							break;
						}
						
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
		
		expressions.put(node, result);
	}
	
	@Override
	public void visit(Subtraction node) {
		super.visit(node);
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
		super.visit(node);
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

	@Override
	public void visit(Division node) {
		super.visit(node);
		System.err.println("Warning: Divison isn't implemented fully yet! Only scalars.");
		
		MvExpressions result = createNewMvExpressions();
		
		MvExpressions l = expressions.get(node.getLeft());
		for (int blade=0;blade<bladeCount;blade++)
			if (l.bladeExpressions[blade] != null)
				result.bladeExpressions[blade] = new Division(l.bladeExpressions[blade],expressions.get(node.getRight()).bladeExpressions[0]);
		
		expressions.put(node, result);
		
	}
	
	@Override
	public void visit(InnerProduct node) {
		super.visit(node);
		calculateUsingMultTable(INNER, node);
	}

	@Override
	public void visit(Multiplication node) {
		super.visit(node);
		calculateUsingMultTable(GEO, node);
	}

	@Override
	public void visit(MathFunctionCall node) {
		super.visit(node);
		

		
		MvExpressions result = createNewMvExpressions();
		switch (node.getFunction()) {
		case ABS:
			//sqrt(abs(op*op))
			
			Expression a = node.getOperand();
			Expression aReverse = new Reverse(a);

			Expression prod = new Multiplication(a, aReverse);
			prod.accept(this);
			
			Expression i0 = expressions.get(prod).bladeExpressions[0];
			
			if (i0 == null) i0 = new FloatConstant(0);
			
			result.bladeExpressions[0] = new MathFunctionCall(new MathFunctionCall(i0,MathFunction.ABS),MathFunction.SQRT);
			
			
			break;
		case SQRT:
			//sqrt(scalar)
			result.bladeExpressions[0] = new MathFunctionCall(expressions.get(node.getOperand()).bladeExpressions[0],MathFunction.SQRT);
			break;
		default:
			System.err.println("MathFunction "+node.getFunction().toString()+" isn't implemented yet!");
			return;
		}
		
		expressions.put(node,result);
	}

	@Override
	public void visit(Variable node) {
		super.visit(node);
		
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
			v.bladeExpressions[0] = node;
		}
		
		expressions.put(node,v);
	}

	@Override
	public void visit(MultivectorComponent node) {
		super.visit(node);
		expressions.put(node,variables.get(node.toString()));
	}

	@Override
	public void visit(Exponentiation node) {
		super.visit(node);
		// TODO Auto-generated method stub
		System.err.println("Exponentiation isn't implemented yet!");
	}

	@Override
	public void visit(FloatConstant node) {
		super.visit(node);
		MvExpressions result = createNewMvExpressions();
		result.bladeExpressions[0] = node;
		expressions.put(node, result);
	}

	@Override
	public void visit(OuterProduct node) {
		super.visit(node);
		calculateUsingMultTable(OUTER, node);
	}

	@Override
	public void visit(BaseVector node) {
		super.visit(node);
	
		MvExpressions result = createNewMvExpressions(); 
		result.bladeExpressions[node.getOrder()] = new FloatConstant(1); //TODO change for other algebras
		expressions.put(node,result);
	}

	@Override
	public void visit(Negation node) {
		super.visit(node);
		MvExpressions op = expressions.get(node.getOperand());
		
		MvExpressions result = createNewMvExpressions();
		
		for (int blade=0;blade<bladeCount;blade++) 
			if (op.bladeExpressions[blade] != null) 
				result.bladeExpressions[blade] = new Negation(op.bladeExpressions[blade]);

		
		expressions.put(node, result);
	}

	@Override
	public void visit(Reverse node) {
		super.visit(node);
		
		MvExpressions op = expressions.get(node.getOperand());
		
		MvExpressions result = createNewMvExpressions();
		
		for (int blade=0;blade<bladeCount;blade++) 
			if (op.bladeExpressions[blade] != null) {
				int k = usedAlgebra.getGrade(blade);
				if (((k*(k-1))/2) % 2 == 0)
					result.bladeExpressions[blade] = op.bladeExpressions[blade];
				else
					result.bladeExpressions[blade] = new Negation(op.bladeExpressions[blade]);
			}
		
		expressions.put(node, result);
		
	}



	@Override
	public void visit(LogicalOr node) {
		super.visit(node);
		// TODO Auto-generated method stub
		System.err.println("LogicalOr isn't implemented yet!");
	}

	@Override
	public void visit(LogicalAnd node) {
		super.visit(node);
		// TODO Auto-generated method stub
		System.err.println("LogicalAnd isn't implemented yet!");
	}

	@Override
	public void visit(LogicalNegation node) {
		super.visit(node);
		// TODO Auto-generated method stub
		System.err.println("LogicalNegation isn't implemented yet!");
	}

	@Override
	public void visit(Equality node) {
		super.visit(node);
		// TODO Auto-generated method stub
		System.err.println("Equality isn't implemented yet!");
	}

	@Override
	public void visit(Inequality node) {
		super.visit(node);
		// TODO Auto-generated method stub
		System.err.println("Inequality isn't implemented yet!");
	}

	@Override
	public void visit(Relation relation) {
		super.visit(relation);
		// TODO Auto-generated method stub
		System.err.println("Relation isn't implemented yet!");
	}

	@Override
	public void visit(FunctionArgument node) {
		super.visit(node);
		// TODO Auto-generated method stub
		System.err.println("FunctionArgument isn't implemented yet!");
	}

	@Override
	public void visit(MacroCall node) {
		super.visit(node);
		// TODO Auto-generated method stub
		System.err.println("MacroCall isn't implemented yet!");
	}
	
}