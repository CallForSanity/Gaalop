package de.gaalop.maple;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;

/**
 * This class is responsible for inlining user-defined macros at places where macros are called.
 * 
 * @author Christian Schwinn
 *
 */
public class InlineMacrosVisitor implements ControlFlowVisitor {

	@Override
	public void visit(StartNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AssignmentNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StoreResultNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IfThenElseNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BlockEndNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(LoopNode loopNode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BreakNode breakNode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(EndNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Macro node) {
		// TODO Auto-generated method stub
		
	}

}
