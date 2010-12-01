package de.gaalop.codegen_verilog.VerilogIR.VerilogNodes;

import de.gaalop.codegen_verilog.VerilogIR.VerilogDFG;




public class VerilogUnaryOperationNode extends VerilogNode {
	
	
	
	private VerilogNode operand;
	
	
	
	
	
	
	
	
	
	public VerilogUnaryOperationNode(VerilogNode operand, VerilogDFG dfg){
		super(dfg);
		this.operand=operand;
		
		
		
		}









	private void setOperand(VerilogNode operand) {
		this.operand = operand;
	}









	private VerilogNode getOperand() {
		return operand;
	}
}
