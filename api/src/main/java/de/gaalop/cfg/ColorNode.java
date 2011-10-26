package de.gaalop.cfg;

import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;

public class ColorNode extends SequentialNode {

        //changed by chs: no reason for making final
	private Expression r;
	private Expression g;
	private Expression b;
	private Expression alpha;
	
	public ColorNode(ControlFlowGraph graph, Expression r, Expression g, Expression b) {
		super(graph);
		this.r = r;
		this.g = g;
		this.b = b;
		this.alpha = new FloatConstant(1f);
	}
	
	public ColorNode(ControlFlowGraph graph, Expression r, Expression g, Expression b, Expression alpha) {
		super(graph);
		this.r = r;
		this.g = g;
		this.b = b;
		this.alpha = alpha;
	}
	
	

	public Expression getR() {
		return r;
	}

	public Expression getG() {
		return g;
	}

	public Expression getB() {
		return b;
	}

	public Expression getAlpha() {
		return alpha;
	}

	@Override
	public ColorNode copyElements() {
		return new ColorNode(getGraph(), r.copy(), g.copy(), b.copy());
	}

	@Override
	public void accept(ControlFlowVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "Color(" + r + ", " + g + ", " + b + ", " + alpha + ")";
	}

        public void setR(Expression r) {
            this.r = r;
        }

        public void setG(Expression g) {
            this.g = g;
        }

        public void setB(Expression b) {
            this.b = b;
        }

        public void setAlpha(Expression alpha) {
            this.alpha = alpha;
        }

        

}
