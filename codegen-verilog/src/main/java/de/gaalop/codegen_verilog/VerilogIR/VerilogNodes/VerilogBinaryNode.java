package de.gaalop.codegen_verilog.VerilogIR.VerilogNodes;

import de.gaalop.codegen_verilog.VerilogIR.VerilogDFG;

public class VerilogBinaryNode extends VerilogNode {

	private VerilogNode left;
	private VerilogNode right;
	private int level;
	
	
	
	public VerilogBinaryNode(VerilogDFG dfg) {
		super(dfg);
		// TODO Auto-generated constructor stub
	}

	public VerilogBinaryNode(VerilogNode left, VerilogNode right, VerilogDFG dfg){
	super(dfg);
	this.setLeft(left);
	this.setRight(right);
	
	
	}

	private void setLeft(VerilogNode left) {
		this.left = left;
	}

	private VerilogNode getLeft() {
		return left;
	}

	private void setRight(VerilogNode right) {
		this.right = right;
	}

	private VerilogNode getRight() {
		return right;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
	
	
}
