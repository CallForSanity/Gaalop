package de.gaalop.dfg;

import java.util.ArrayList;
import java.util.List;

import de.gaalop.cfg.SequentialNode;

public class MacroCall extends Expression {
	
	private String name;
	private List<Expression> args;
	private boolean singleLine;
	private SequentialNode caller;
	
	public MacroCall(String name, List<Expression> args) {
		this.name = name;
		this.args = args;
	}
	
	public void setCaller(SequentialNode caller) {
		this.caller = caller;
	}
	
	public SequentialNode getCaller() {
		return caller;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Expression> getArguments() {
		return args;
	}
	
	public void setSingleLine() {
		this.singleLine = true;
	}
	
	public boolean isSingleLine() {
		return singleLine;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Expression copy() {
		// create a deep copy of args list
		List<Expression> newArgs = new ArrayList<Expression>();
		for (Expression e : args) {
			newArgs.add(e.copy());
		}
		return new MacroCall(name, newArgs);
	}
	
	@Override
	public void replaceExpression(Expression old, Expression newExpression) {
		for (int i = 0; i < args.size(); i++) {
			Expression arg = args.get(i);
			if (arg == old) {
				args.set(i, newExpression);
			} else {				
				arg.replaceExpression(old, newExpression);
			}			
		}
	}

	@Override
	public boolean isComposite() {
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append('(');
		int num = args.size();
		int i = 0;
		for (Expression e : args) {
			sb.append(e);
			if (i < num - 1) {
				sb.append(',');
			}
			i++;
		}
		sb.append(')');
		return sb.toString();
	}

}
