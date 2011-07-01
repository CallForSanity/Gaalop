package de.gaalop.tba.cfgImport;

import java.util.HashMap;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;

public class CFGVisitorImport extends EmptyControlFlowVisitor {

	private DFGVisitorImport cfgExpressionVisitor;
	
	public HashMap<String,MvExpressions> variables;
	
	public CFGVisitorImport(DFGVisitorImport cfgExpressionVisitor) {
		this.cfgExpressionVisitor = cfgExpressionVisitor;
		variables = new HashMap<String, MvExpressions>();
	}
	
	@Override
	public void visit(AssignmentNode node) {
		Variable variable = node.getVariable();
		Expression value = node.getValue();

		cfgExpressionVisitor.setCurrentOutputVariable(variable);
		cfgExpressionVisitor.variables = variables;
		value.accept(cfgExpressionVisitor);
		
		MvExpressions mvExpr = cfgExpressionVisitor.expressions.get(value);
		variables.put(variable.toString(), mvExpr);
		
		
		
		AssignmentNode lastNode = node;
		
		
		
		for (int i=0;i<cfgExpressionVisitor.bladeCount;i++) 
		{
			
			Expression e = mvExpr.bladeExpressions[i];
			
			if (e!=null) {
				AssignmentNode insNode = new AssignmentNode(node.getGraph(), new MultivectorComponent(variable.getName(),i),e);
				
				lastNode.insertAfter(insNode);
				lastNode = insNode;
			}
			/*
			if (e == null) e = new FloatConstant(0);
			AssignmentNode insNode = new AssignmentNode(node.getGraph(), new MultivectorComponent(variable.getName(),i),e);
			
			lastNode.insertAfter(insNode);
			lastNode = insNode;
			*/
		}
		
		node.getGraph().removeNode(node);
		
		lastNode.getSuccessor().accept(this);
	}
	
}
