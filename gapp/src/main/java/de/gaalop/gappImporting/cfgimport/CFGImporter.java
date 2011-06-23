package de.gaalop.gappImporting.cfgimport;

import java.util.HashMap;

import de.gaalop.tba.Multivector;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.Node;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.Equality;
import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionFactory;
import de.gaalop.dfg.FloatConstant;
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

public class CFGImporter {

	private final double EPSILON = 10E-07;
	private UseAlgebra usedAlgebra;

	private HashMap<String,MvExpressions> mapExpr;
	
	public ControlFlowGraph importGraph(ControlFlowGraph graph) {
		mapExpr = new HashMap<String, MvExpressions>();
		ControlFlowGraph result = new ControlFlowGraph();
		result.setSignature(graph.getSignature());
		//GAPPProgram program = new GAPPProgram();
		//program.setAlgebraSignature(graph.getSignature());
		
		usedAlgebra = new UseAlgebra();
		usedAlgebra.load5dAlgebra();		
		
		//do the table based calculation
		
		Node nodeCur = graph.getStartNode().getSuccessor();
		
		while (nodeCur instanceof SequentialNode) {
			//assumption conformal N3
			SequentialNode curSeqNode = (SequentialNode) nodeCur;
			
			importSeqNode(curSeqNode,result);
			
			nodeCur = curSeqNode.getSuccessor();
		}

		//return program;
		return result;
	}
	
	
	private void importSeqNode(SequentialNode curSeqNode,ControlFlowGraph result) {
		
		if (curSeqNode instanceof AssignmentNode) {
			AssignmentNode curAssNode = (AssignmentNode) curSeqNode;
			MvExpressions mv = importExpression(curAssNode.getValue(),result);
			mapExpr.put(curAssNode.getVariable().getName(),mv);
		} else 
			if (curSeqNode instanceof StoreResultNode) {
				StoreResultNode curStoreNode = (StoreResultNode) curSeqNode;
				MvExpressions mv = mapExpr.get(curStoreNode.getValue().getName());
				
				String resultVarName = curStoreNode.getValue().getName();
				for (int i=0;i<usedAlgebra.getBladeCount();i++) {
					AssignmentNode node = new AssignmentNode(result, new MultivectorComponent(resultVarName,i), mv.bladeExpressions[i]);
					if (mv.bladeExpressions[i] != null)
						addNodeAtEnd(node, result);
					
				}
			}
		
		
	}
	
	private MvExpressions createNewMv() {
		return new MvExpressions(null,usedAlgebra.getBladeCount());
	}

	private MvExpressions importExpression(Expression value, ControlFlowGraph result) {
		
		
		if (value instanceof UnaryOperation) {
			//System.out.println("UnaryExpression "+value.toString());
			UnaryOperation op = (UnaryOperation) value;
			
			
			MvExpressions mvOp = importExpression(op.getOperand(), result);

			return importUnary(op,mvOp);
		} else
			if (value instanceof BinaryOperation) {
				//System.out.println("BinaryExpression "+value.toString());
				BinaryOperation op = (BinaryOperation) value;
				
				MvExpressions mvOpL = importExpression(op.getLeft(), result);
				MvExpressions mvOpR = importExpression(op.getRight(), result);

				return importBinary(op,mvOpL,mvOpR);
			} else 
				if (value instanceof BaseVector) {
					
					BaseVector base = (BaseVector) value;
					//System.out.println("BaseVector "+base.toString()+": "+base.getOrder());
					MvExpressions mv = createNewMv(); 
					mv.bladeExpressions[base.getOrder()] = new FloatConstant(1);
					return mv;
				} else 
					if (value instanceof FloatConstant) {
						FloatConstant floatConst = (FloatConstant) value;
						MvExpressions mv = createNewMv();
						mv.bladeExpressions[0] = new FloatConstant(floatConst.getValue());
						return mv;
					} else 
						if (value instanceof Variable) {
							Variable var = (Variable) value;
							//System.out.println("Variable "+var.getName());
							return getMvExpressionOfVariable(var);
						} else 
							if (value instanceof MacroCall) {
								MacroCall macro = (MacroCall) value;
								//System.out.println("Variable "+var.getName());
								//TODO implement macros
								System.err.println("Macros are not implemented yet");
								return null;
							} else {
								System.err.println("Unknown: "+value);
								return null;
							}
		

		
	}
	
	private MvExpressions importUnary(UnaryOperation op,MvExpressions mvOp) {
		MvExpressions result = createNewMv();
		if (op instanceof LogicalNegation) {
			System.err.println("LogicalNegation not implemented");
		} else
			if (op instanceof MathFunctionCall) {
				result.bladeExpressions[0] = new MathFunctionCall(mvOp.bladeExpressions[0],((MathFunctionCall) op).getFunction());
			} else
				if (op instanceof Negation) {
					return importNegation(mvOp);
				} else
					if (op instanceof Reverse) {
						System.err.println("Reverse not implemented");
					} else {
						System.err.println("Unknown unary operation "+op.toString());
						return null;
					}
		return result;
	}
	
	private MvExpressions importNegation(MvExpressions mvOp) {
		MvExpressions result = createNewMv();
		int bladeCount = result.bladeExpressions.length;
		
		for (int blade = 0;blade<bladeCount;blade++) {
			if (mvOp.bladeExpressions[blade] != null) 
				result.bladeExpressions[blade] = ExpressionFactory.subtract(new FloatConstant(0),mvOp.bladeExpressions[blade]);					
		}
		
		return result;
	}

	private MvExpressions importAddition(MvExpressions mvOpL, MvExpressions mvOpR) {
		MvExpressions result = createNewMv();
		int bladeCount = result.bladeExpressions.length;
		for (int blade = 0;blade<bladeCount;blade++) {
			if (mvOpL.bladeExpressions[blade] == null) 
				result.bladeExpressions[blade] = mvOpR.bladeExpressions[blade];
			else 
				if (mvOpR.bladeExpressions[blade] == null) 
					result.bladeExpressions[blade] = mvOpL.bladeExpressions[blade];
				else 
					result.bladeExpressions[blade] = ExpressionFactory.sum(mvOpL.bladeExpressions[blade],mvOpR.bladeExpressions[blade]);					
		}
		return result;
	}
	
	private MvExpressions importDivision(MvExpressions mvOpL, MvExpressions mvOpR) {
		MvExpressions result = createNewMv();
		int bladeCount = result.bladeExpressions.length;
		
		for (int blade = 1;blade<bladeCount;blade++) {
			if (mvOpR.bladeExpressions[blade] != null)
				System.err.println("Warning: Divisions of multivectors are not implemented");
		}
		
		
		for (int blade = 0;blade<bladeCount;blade++) {
			if (mvOpL.bladeExpressions[blade] != null) 
				result.bladeExpressions[blade] = ExpressionFactory.divide(mvOpL.bladeExpressions[blade],mvOpR.bladeExpressions[0]);
		}
		
		return result;
	}
	
	private MvExpressions importSubtraction(MvExpressions mvOpL, MvExpressions mvOpR) {
		MvExpressions result = createNewMv();
		int bladeCount = result.bladeExpressions.length;
		for (int blade = 0;blade<bladeCount;blade++) {
			if (mvOpL.bladeExpressions[blade] == null) 
				result.bladeExpressions[blade] = ExpressionFactory.subtract(new FloatConstant(0),mvOpR.bladeExpressions[blade]);
			else 
				if (mvOpR.bladeExpressions[blade] == null) 
					result.bladeExpressions[blade] = mvOpL.bladeExpressions[blade];
				else 
					result.bladeExpressions[blade] = ExpressionFactory.subtract(mvOpL.bladeExpressions[blade],mvOpR.bladeExpressions[blade]);					
		}
		return result;
	}
	
	private MvExpressions importInnerProduct(MvExpressions mvOpL, MvExpressions mvOpR) {
		MvExpressions result = createNewMv();
		int bladeCount = result.bladeExpressions.length;
		for (int blade1 = 0;blade1<bladeCount;blade1++) 
			if (mvOpL.bladeExpressions[blade1] != null)
			{
				for (int blade2 = 0;blade2<bladeCount;blade2++) {
					if (mvOpR.bladeExpressions[blade2] != null)
					{
						Expression prodExpr = ExpressionFactory.product(mvOpL.bladeExpressions[blade1],mvOpR.bladeExpressions[blade2]);
						
						double[] prod = usedAlgebra.inner(blade1, blade2).getValueArr();
						for (int bladeR = 0;bladeR<bladeCount;bladeR++) {
							if (Math.abs(prod[bladeR])>EPSILON) {
								if (result.bladeExpressions[bladeR] == null) {
									result.bladeExpressions[bladeR] = ExpressionFactory.product(prodExpr,new FloatConstant((float) prod[bladeR]));
								} else {
									result.bladeExpressions[bladeR] = ExpressionFactory.sum(result.bladeExpressions[bladeR],ExpressionFactory.product(prodExpr,new FloatConstant((float) prod[bladeR])));
								}
							}
						}
					}
				}
			}
		return result;
	}
	
	private MvExpressions importOuterProduct(MvExpressions mvOpL, MvExpressions mvOpR) {
		MvExpressions result = createNewMv();
		int bladeCount = result.bladeExpressions.length;
		for (int blade1 = 0;blade1<bladeCount;blade1++) 
			if (mvOpL.bladeExpressions[blade1] != null)
			{
				for (int blade2 = 0;blade2<bladeCount;blade2++) {
					if (mvOpR.bladeExpressions[blade2] != null)
					{
						Expression prodExpr = ExpressionFactory.product(mvOpL.bladeExpressions[blade1],mvOpR.bladeExpressions[blade2]);
						
						double[] prod = usedAlgebra.outer(blade1, blade2).getValueArr();
						for (int bladeR = 0;bladeR<bladeCount;bladeR++) {
							if (Math.abs(prod[bladeR])>EPSILON) {
								if (result.bladeExpressions[bladeR] == null) {
									result.bladeExpressions[bladeR] = ExpressionFactory.product(prodExpr,new FloatConstant((float) prod[bladeR]));
								} else {
									result.bladeExpressions[bladeR] = ExpressionFactory.sum(result.bladeExpressions[bladeR],ExpressionFactory.product(prodExpr,new FloatConstant((float) prod[bladeR])));
								}
							}
						}
					}
				}
			}
		return result;
	}
	
	private MvExpressions importGeoProduct(MvExpressions mvOpL, MvExpressions mvOpR) {
		MvExpressions result = createNewMv();
		int bladeCount = result.bladeExpressions.length;
		for (int blade1 = 0;blade1<bladeCount;blade1++) 
			if (mvOpL.bladeExpressions[blade1] != null)
			{
				for (int blade2 = 0;blade2<bladeCount;blade2++) {
					if (mvOpR.bladeExpressions[blade2] != null)
					{
						Expression prodExpr = ExpressionFactory.product(mvOpL.bladeExpressions[blade1],mvOpR.bladeExpressions[blade2]);
						
						double[] prod = usedAlgebra.geo(blade1, blade2).getValueArr();
						for (int bladeR = 0;bladeR<bladeCount;bladeR++) {
							if (Math.abs(prod[bladeR])>EPSILON) {
								if (result.bladeExpressions[bladeR] == null) {
									result.bladeExpressions[bladeR] = ExpressionFactory.product(prodExpr,new FloatConstant((float) prod[bladeR]));
								} else {
									result.bladeExpressions[bladeR] = ExpressionFactory.sum(result.bladeExpressions[bladeR],ExpressionFactory.product(prodExpr,new FloatConstant((float) prod[bladeR])));
								}
							}
						}
					}
				}
			}
		return result;
	}

	private MvExpressions importBinary(BinaryOperation op,MvExpressions mvOpL, MvExpressions mvOpR) {

		if (op instanceof Addition) {
			return importAddition(mvOpL, mvOpR);
		} else
			if (op instanceof Division) {
				return importDivision(mvOpL, mvOpR);
			} else
				if (op instanceof Equality) {
					System.err.println("Equality not implemented");
					return null;
				} else
					if (op instanceof Exponentiation) {
						System.err.println("Exponentiation not implemented");
						return null;
					} else
						if (op instanceof Inequality) {
							System.err.println("Inequality not implemented");
							return null;
						} else
							if (op instanceof InnerProduct) {
								return importInnerProduct(mvOpL, mvOpR);
							} else
								if (op instanceof LogicalAnd) {
									System.err.println("LogicalAnd not implemented");
									return null;
								} else
									if (op instanceof LogicalOr) {
										System.err.println("LogicalOr not implemented");
										return null;
									} else
										if (op instanceof Multiplication) {
											return importGeoProduct(mvOpL, mvOpR);
										} else
											if (op instanceof OuterProduct) {
												return importOuterProduct(mvOpL, mvOpR);
											} else
												if (op instanceof Relation) {
													System.err.println("Relation not implemented");
													return null;
												} else
													if (op instanceof Subtraction) {
														return importSubtraction(mvOpL, mvOpR);
													} else {
														System.err.println("Unknown binary operation "+op.toString());
														return null;
													}
	
	}


	private MvExpressions getMvExpressionOfVariable(Variable v) {
		if (!mapExpr.containsKey(v.getName())) {
			MvExpressions result = createNewMv();
			result.bladeExpressions[0] = v;
			mapExpr.put(v.getName(),result);
			return result;
		} else	
			return mapExpr.get(v.getName());
	}
	
	private void addNodeAtEnd(SequentialNode newNode,ControlFlowGraph result) {
		result.getEndNode().insertBefore(newNode);
	}

}
