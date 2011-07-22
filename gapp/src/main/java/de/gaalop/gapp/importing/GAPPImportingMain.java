package de.gaalop.gapp.importing;

import de.gaalop.dfg.DivisionRemover;
import de.gaalop.dfg.ExpressionRemover;
import de.gaalop.OptimizationException;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.tba.cfgImport.MvExpressionsBuilder;
import de.gaalop.tba.Plugin;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Facade class to import the ControlFlowGraph in a GAPP ControlFlowGraph
 * @author christian
 */
public class GAPPImportingMain {

    /**
     * Imports a given ControlFlowGraph in a GAPP ControlFlowGraph
     * @param graph The ControlFlowGraph
     * @return The same graph object (which is now decorated with GAPP instructions)
     * @throws OptimizationException
     */
    public ControlFlowGraph importGraph(ControlFlowGraph graph) throws OptimizationException {

        ExpressionRemover remover = new DivisionRemover();
        graph.accept(remover);

        Splitter splitter = new Splitter(graph);
        graph.accept(splitter);

        // do a full tba visit on graph to calculate MvExpressions
   /*     Plugin plugin = new Plugin();
        de.gaalop.tba.cfgImport.CFGImporter importer = new de.gaalop.tba.cfgImport.CFGImporter(true,plugin);
        importer.importGraph(graph);
        DFGVisitorImport vDFG = importer.getvDFG();*/

        Plugin plugin = new Plugin();
        de.gaalop.tba.cfgImport.CFGImporter importer2 = new de.gaalop.tba.cfgImport.CFGImporter(true,plugin);

        MvExpressionsBuilder builder = new MvExpressionsBuilder(true, importer2.getUsedAlgebra());
        graph.accept(builder);

        // import now the graph in GAPP
        GAPPImporter vCFG = new GAPPImporter(importer2.getUsedAlgebra(),builder.expressions);
        graph.accept(vCFG);

        return graph;
    }

    /**
     * Prints out the assigned flags for all multivector in the given map.
     *
     * This method is commonly used for debug purposes
     * @param assigned The map which contains the assigned flags for multivectors, which should be printed out
     */
    private void outputAssignedArray(HashMap<GAPPMultivector, BooleanArr> assigned) {
         GAPPMultivector[] arr = assigned.keySet().toArray(new GAPPMultivector[0]);

        //Sort the names alphabetically by name of the multivectors
        Arrays.sort(arr,new Comparator<GAPPMultivector>() {
            @Override
            public int compare(GAPPMultivector o1, GAPPMultivector o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });


        for (GAPPMultivector cur: arr) {
            BooleanArr val = assigned.get(cur);

            System.out.print(cur.getName()+":");
            for (boolean cur1: val.getComponents()) {
                System.out.print((cur1) ? "1" : "0");
            }
            System.out.println();

        }
    }

}
