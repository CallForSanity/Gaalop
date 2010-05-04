package de.gaalop.codegen_verilog.VerilogIR.VerilogNodes;

import de.gaalop.codegen_verilog.VerilogIR.VerilogDFG;


public class VerilogFloatNode extends VerilogNode{
private Float value;

	public VerilogFloatNode(VerilogDFG dfg) {
		super(dfg);
		// TODO Auto-generated constructor stub
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public Float getValue() {
		return value;
	}

	
	
}
