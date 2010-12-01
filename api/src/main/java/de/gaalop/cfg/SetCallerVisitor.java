package de.gaalop.cfg;

import de.gaalop.dfg.UpdateMacroCallVisitor;

public class SetCallerVisitor extends EmptyControlFlowVisitor {
	
	@Override
	public void visit(AssignmentNode node) {
		UpdateMacroCallVisitor updater = new UpdateMacroCallVisitor(node);
		node.getValue().accept(updater);
		node.getSuccessor().accept(this);
	}
	
	@Override
	public void visit(ExpressionStatement node) {
		UpdateMacroCallVisitor updater = new UpdateMacroCallVisitor(node);
		node.getExpression().accept(updater);
		node.getSuccessor().accept(this);
	}
	
}