package de.gaalop.gappImporting;

import java.util.LinkedList;

import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
import de.gaalop.gapp.GAPP;


public class GAPPImporter {

	public GAPP importGraph(ControlFlowGraph graph) {
		GAPP result = new GAPP();
		//result.setAlgebraSignature(graph.getSignature());
		
		
		Node nodeCur = graph.getStartNode().getSuccessor();
		int no=1;
		while (nodeCur instanceof SequentialNode) {
			//assumption conformal N3
			SequentialNode curSeqNode = (SequentialNode) nodeCur;
			
			importSeqNode(no,curSeqNode,result);
			
			nodeCur = curSeqNode.getSuccessor();
			no++;
		}		
		
		return result;
	}

	private void importSeqNode(int no,SequentialNode curSeqNode, GAPP result) {
		if (curSeqNode instanceof AssignmentNode) 
			importAssignmentNode(no,(AssignmentNode) curSeqNode, result);
		else 
			if (curSeqNode instanceof StoreResultNode) 
				importStoreResultNode((StoreResultNode) curSeqNode, result);
			
	}

	private void importAssignmentNode(int no,AssignmentNode node, GAPP result) {
		Variable variable = node.getVariable();
		Expression value = node.getValue();
		importExpression(variable,no,value, result);
	}
	
	private void importStoreResultNode(StoreResultNode node, GAPP result) {
		// TODO Auto-generated method stub
		
	}
	
	private void importExpression(Variable target,int no,Expression expr, GAPP result) {
		if (expr instanceof Addition) { //Special case: Addition (-> dotVectors can directly operate)
			importAddition(target,no,(Addition) expr,result);
		} else {
			//TODO
		}
	}

	private void importAddition(Variable target,int no,Addition expr, GAPP result) {
		LinkedList<Expression> listSummands = new LinkedList<Expression>();
		getSummandList(expr.getLeft(),listSummands);
		getSummandList(expr.getRight(),listSummands);
		
		//listSummands contains now all summands in this expression
		//now the summands are evaluated first 
		
		Variable[] variables = new Variable[listSummands.size()];
		
		int i=0;
		for (Expression curSummand: listSummands) {
			variables[i] = new Variable("tmp"+no+"_"+i); //create target
			importExpression(variables[i], no, curSummand, result);
			i++;
		}
		
		//and just now the setVector/dotVectors operation can be done
		//TODO setVector
		//Todo optimiere vielleicht noch bei echtem dotproduct
		
		//TODO dotVector
		
		
		
	}

	private void getSummandList(Expression expr,
			LinkedList<Expression> listSummands) {
		listSummands.add(expr);
		if (expr instanceof Addition) {
			getSummandList(((Addition) expr).getLeft(),listSummands);
			getSummandList(((Addition) expr).getRight(),listSummands);
		}
		
	}
	
}
