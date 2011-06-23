package de.gaalop.gappImporting.cfgimport;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;

public class CFGImporter2 {

	public ControlFlowGraph importGraph(ControlFlowGraph graph) {
		CFGExpressionVisitor vDFG = new CFGExpressionVisitor();
		ControlFlowVisitor vCFG = new CFGVisitorImport(vDFG);
		graph.accept(vCFG);
		return graph;
	}

}
