package de.gaalop.codegen_verilog.VerilogIR.VerilogNodes;

import java.util.Vector;

import de.gaalop.cfg.Node;
import de.gaalop.codegen_verilog.VerilogIR.VerilogDFG;
import de.gaalop.dfg.Expression;






public class VerilogNode {
	
	private Node embeddednode;
	private Vector<VerilogNode> predecessors= new Vector();
	private Vector<VerilogNode> successors = new Vector();
	private int level;
	private boolean isMemberOfExpression=false;
	private VerilogDFG dfg;
	private Expression expnode;
	private String varname;

	
	
	
	
	public VerilogNode(VerilogDFG dfg) {
		super();
		this.dfg = dfg;
	}

	public void setVarname(String varname) {
		this.varname = varname;
	}

	public String getVarname() {
		return varname;
	}

	public void setNode(Expression n){
		this.expnode=n;
		}
	
	public Expression getNode(){
		return this.expnode;
		}
	
	
	
	
public VerilogNode[] getSuccessors(){
	return (VerilogNode[]) successors.toArray();
	}
	
	
	
	public VerilogNode[] getPredecessors(){
		return (VerilogNode[])predecessors.toArray(new VerilogNode[0]);

}

	
	public void newDFGPredecessor (VerilogNode n){
		predecessors.add(n);
						
		
	}
	
	public void newDFGSuccesor(VerilogNode n){

		successors.add(n);
		
						
		
	}
	
	public void appendDFGNode (VerilogNode n){ 
	System.out.println("Appending Successor: " + n.getVarname()+ " to: " + this.getClass().getSimpleName()   );
	successors.add(n);
	n.newDFGPredecessor(this);
	}
	 
	
	public void insertBeforeDFGNode (VerilogNode n){
	predecessors.add(n);
}

public void setlevel (int l)
{
	this.level = l;

	}

public int getlevel ()
{
	return this.level;

	}	

 }

