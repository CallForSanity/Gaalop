package de.gaalop.maple;

import de.gaalop.cfg.*;
import de.gaalop.dfg.*;

import java.util.HashSet;
import java.util.Set;

/**
 * This visitor traverses a control and dataflow graph and collects information about the used variables. At the end of the
 * traversal, it rebuilds the input, local and output variable sets of the graph.
 */
public class RemoveUnusedVariablesVisitor implements ControlFlowVisitor, ExpressionVisitor {

	private Set<Variable> referencedVariables = new HashSet<Variable>();

	@Override
	public void visit(StartNode node) {
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(AssignmentNode node) {
		node.getVariable().accept(this);
		node.getValue().accept(this);
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(StoreResultNode node) {
		node.getValue().accept(this);
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(IfThenElseNode node) {
		node.getPositive().accept(this);
		node.getNegative().accept(this);
	}

	@Override
	public void visit(LoopNode node) {
		node.getBody().accept(this);
	}

	@Override
	public void visit(BreakNode breakNode) {
		// nothing to do
	}

	@Override
	public void visit(BlockEndNode node) {
	}

	@Override
	public void visit(EndNode node) {
		Set<Variable> localVariables = new HashSet<Variable>(node.getGraph().getLocalVariables());
		for (Variable var : localVariables) {
			if (!referencedVariables.contains(var)) {
				node.getGraph().removeLocalVariable(var);
			}
		}
	}

	@Override
	public void visit(Subtraction node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
	}

	@Override
	public void visit(Addition node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
	}

	@Override
	public void visit(Division node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
	}

	@Override
	public void visit(InnerProduct node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
	}

	@Override
	public void visit(Multiplication node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
	}

	@Override
	public void visit(MathFunctionCall node) {
		node.getOperand().accept(this);
	}

	@Override
	public void visit(Variable node) {
		referencedVariables.add(node);
	}

	@Override
	public void visit(MultivectorComponent node) {
		referencedVariables.add(new Variable(node.getName()));
	}

	@Override
	public void visit(Exponentiation node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
	}

	@Override
	public void visit(FloatConstant node) {
	}

	@Override
	public void visit(OuterProduct node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
	}

	@Override
	public void visit(BaseVector node) {
	}

	@Override
	public void visit(Negation node) {
		node.getOperand().accept(this);
	}

	@Override
	public void visit(Reverse node) {
		node.getOperand().accept(this);
	}

	@Override
	public void visit(LogicalOr node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
	}

	@Override
	public void visit(LogicalAnd node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
	}

	@Override
	public void visit(Equality node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
	}

	@Override
	public void visit(Inequality node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
	}

	@Override
	public void visit(Relation node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
	}

	@Override
	public void visit(Macro node) {
		throw new IllegalStateException("Macros should have been inlined.");
	}

	@Override
	public void visit(FunctionArgument node) {
		throw new IllegalStateException("Macros should have been inlined, so function arguments are not allowed here.");
	}

	@Override
	public void visit(MacroCall node) {
		// TODO Auto-generated method stub
		
	}
}
