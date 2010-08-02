package de.gaalop.dfg;

import de.gaalop.cfg.SequentialNode;

public class UpdateMacroCallVisitor extends EmptyExpressionVisitor {
	
	private final SequentialNode caller;
	
	public UpdateMacroCallVisitor(SequentialNode caller) {
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