package de.gaalop;

import java.util.HashSet;
import java.util.Set;

import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.EmptyExpressionVisitor;
import de.gaalop.dfg.InnerProduct;
import de.gaalop.dfg.OuterProduct;
import de.gaalop.dfg.Reverse;
import de.gaalop.dfg.Variable;

/**
 * Simple visitor to check if an expression contains Geometric Algebra. Therefore, operations like {@link InnerProduct}
 * or {@link Reverse} are checked. Variables from the left-hand side of an assignment can be explicitly added by
 * {@link #addGAVariable(Variable)}.
 * 
 * @author Christian Schwinn
 * 
 */
public class CheckGAVisitor extends EmptyExpressionVisitor {

	private static Set<Variable> gaVariables = new HashSet<Variable>();
	private boolean isGA = false;

	@Override
	public void visit(BaseVector node) {
		isGA = true;
	}

	@Override
	public void visit(InnerProduct node) {
		isGA = true;
	}

	@Override
	public void visit(OuterProduct node) {
		isGA = true;
	}

	@Override
	public void visit(Reverse node) {
		isGA = true;
	}

	@Override
	public void visit(Variable node) {
		if (gaVariables.contains(node)) {
			isGA = true;
		}
	}

	/**
	 * @return whether the expression visited by this class has been determined to include Geometric Algebra
	 */
	public boolean isGA() {
		return isGA;
	}

	/**
	 * Adds a variable explicitly to the set of variables which represent Geometric Algebra expressions.
	 * 
	 * @param v variable which includes Geometric Algebra
	 */
	public void addGAVariable(Variable v) {
		gaVariables.add(v);
	}

	/**
	 * Checks if the given variable represents a Geometric Algebra expression.
	 * 
	 * @param v variable to be checked
	 * @return true if variable represents a Geometric Algebra expression
	 */
	public static boolean isGAVariable(Variable v) {
		return gaVariables.contains(v);
	}
}