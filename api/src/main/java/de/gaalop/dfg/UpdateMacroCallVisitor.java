package de.gaalop.dfg;

import de.gaalop.cfg.AssignmentNode;

public class UpdateMacroCallVisitor extends EmptyExpressionVisitor {
	
	private final AssignmentNode caller;
	
	public UpdateMacroCallVisitor(AssignmentNode caller) {
		this.caller = caller;
	}
	
	@Override
	public void visit(MacroCall node) {
		node.setCaller(caller);
		for (Expression arg : node.getArguments()) {
			arg.accept(this);
		}
	}
}