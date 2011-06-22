package de.gaalop.tba;

import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.gappZwei.cfgimport.CFGImporter2;
import de.gaalop.gappZwei.cfgimport.ConstantFolding;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GAPPOptStrat implements OptimizationStrategy {

	@Override
	public void transform(ControlFlowGraph graph) throws OptimizationException {
        PrintWriter out = null;
        try {
            CFGImporter2 importer = new CFGImporter2();
            importer.importGraph(graph);
            out = new PrintWriter("/daten/Test.txt");
            out.println("Ich wurde aufgerufen!");
            out.close();
            ConstantFolding folding = new ConstantFolding();
            graph.accept(folding);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GAPPOptStrat.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
        }

}
