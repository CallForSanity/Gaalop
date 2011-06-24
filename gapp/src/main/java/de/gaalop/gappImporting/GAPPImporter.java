/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gappImporting;

import de.gaalop.cfg.ControlFlowGraph;

/**
 *
 * @author christian
 */
public class GAPPImporter {

    	public ControlFlowGraph importGraph(ControlFlowGraph graph) {
		DFGImporter vDFG = new DFGImporter();
		CFGImporter vCFG = new CFGImporter(vDFG);
		graph.accept(vCFG);
		return graph;
	}

}
