package de.gaalop.codegen_verilog.VerilogIR;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.Node;
import de.gaalop.codegen_verilog.VerilogIR.VerilogNodes.VerilogNode;

public class VerilogDFG {

	

	public HashSet<VerilogNode> myhashset;
	public HashMap<VerilogNode,Integer> mymap;
	public Vector <VerilogNode> myvector;
	public HashSet<VerilogNode> unlinkedNodes;
	public HashSet<VerilogNode> linkedNodes;
	private VerilogNode startnode;
	private VerilogNode endnode;
	private Node originalstart;
	private Node originalend;
	private ControlFlowGraph cfg;
	private VerilogIRConverterVisitorCookies irvisit;
	


	public VerilogDFG(ControlFlowGraph in) {
		this.setCfg(in);
		originalstart = in.getStartNode();
		originalend = in.getEndNode();
		this.startnode= new VerilogNode(this) ;
		this.endnode = new VerilogNode(this);
		irvisit = new VerilogIRConverterVisitorCookies(this);
    	in.accept(irvisit);
		
		// TODO Auto-generated constructor stub
	
		
	}

	
	
	public VerilogIRConverterVisitorCookies getIrvisit() {
		return irvisit;
	}



	/**
	 * @param startnode the startnode to set
	 */
	public void setStartnode(VerilogNode startnode) {
		this.startnode = startnode;
	}


	/**
	 * @return the startnode
	 */
	public VerilogNode getStartnode() {
		return startnode;
	}
	
	public HashSet<VerilogNode> getMyhashset() {
		return myhashset;
	}


	public void setMyhashset(HashSet<VerilogNode> myhashset) {
		this.myhashset = myhashset;
	}


	public HashMap<VerilogNode, Integer> getMymap() {
		return mymap;
	}


	public void setMymap(HashMap<VerilogNode, Integer> mymap) {
		this.mymap = mymap;
	}


	public Vector<VerilogNode> getMyvector() {
		return myvector;
	}


	public void setMyvector(Vector<VerilogNode> myvector) {
		this.myvector = myvector;
	}


	public HashSet<VerilogNode> getUnlinkedNodes() {
		return unlinkedNodes;
	}


	public void setUnlinkedNodes(HashSet<VerilogNode> unlinkedNodes) {
		this.unlinkedNodes = unlinkedNodes;
	}


	public HashSet<VerilogNode> getLinkedNodes() {
		return linkedNodes;
	}


	public void setLinkedNodes(HashSet<VerilogNode> linkedNodes) {
		this.linkedNodes = linkedNodes;
	}



	public void setCfg(ControlFlowGraph cfg) {
		this.cfg = cfg;
	}



	public ControlFlowGraph getCfg() {
		return cfg;
	}

}
