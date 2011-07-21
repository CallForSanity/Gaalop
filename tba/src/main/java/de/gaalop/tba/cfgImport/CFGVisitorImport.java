package de.gaalop.tba.cfgImport;

import java.util.HashMap;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import java.util.LinkedList;

public class CFGVisitorImport extends EmptyControlFlowVisitor {

	private DFGVisitorImport cfgExpressionVisitor;
	
	public HashMap<String,MvExpressions> variables;

        private boolean getOnlyMvExpressions;
	
	public CFGVisitorImport(DFGVisitorImport cfgExpressionVisitor, boolean getOnlyMvExpressions) {
		this.cfgExpressionVisitor = cfgExpressionVisitor;
		variables = new HashMap<String, MvExpressions>();
                this.getOnlyMvExpressions = getOnlyMvExpressions;
	}
	
	@Override
	public void visit(AssignmentNode node) {
		Variable variable = node.getVariable();
		Expression value = node.getValue();

		
		cfgExpressionVisitor.variables = variables;
		value.accept(cfgExpressionVisitor);
		
		MvExpressions mvExpr = cfgExpressionVisitor.expressions.get(value);
		variables.put(variable.toString(), mvExpr);
		
		AssignmentNode lastNode = node;

                if (!getOnlyMvExpressions) {
		
	
		// At first, output all assignments
		for (int i=0;i<cfgExpressionVisitor.bladeCount;i++) 
		{
			
			Expression e = mvExpr.bladeExpressions[i];
			
			if (e!=null) {
				AssignmentNode insNode = new AssignmentNode(node.getGraph(), new MultivectorComponent(variable.getName(),i),e);
				
				lastNode.insertAfter(insNode);
				lastNode = insNode;
			} 
			
		}

                // zero all null expressions
                // isn't necessary, because MultipleAssignments aren't allowed
                /*
                for (int i=0;i<cfgExpressionVisitor.bladeCount;i++)
		{

			Expression e = mvExpr.bladeExpressions[i];

			if (e==null) 
                        {
                            AssignmentNode insNode = new AssignmentNode(node.getGraph(), new MultivectorComponent(variable.getName(),i),new FloatConstant(0.0f));

				lastNode.insertAfter(insNode);
				lastNode = insNode;
                        }

		}
                */
		
		node.getGraph().removeNode(node);
            }
		
		lastNode.getSuccessor().accept(this);
	}
	
}
