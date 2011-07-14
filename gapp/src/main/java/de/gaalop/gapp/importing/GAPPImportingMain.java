package de.gaalop.gapp.importing;

import de.gaalop.dfg.DivisionRemover;
import de.gaalop.dfg.ExpressionRemover;
import de.gaalop.OptimizationException;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.tba.cfgImport.DFGVisitorImport;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.SortedSet;


/**
 * Facade class to import the ControlFlowGraph in a GAPP ControlFlowGraph
 * @author christian
 */
public class GAPPImportingMain {

    	public ControlFlowGraph importGraph(ControlFlowGraph graph) throws OptimizationException {

            ExpressionRemover remover = new DivisionRemover();
            graph.accept(remover);

            Splitter splitter = new Splitter(graph);
            graph.accept(splitter);

            // do a full tba visit on graph to calculate MvExpressions
            de.gaalop.tba.cfgImport.CFGImporter importer = new de.gaalop.tba.cfgImport.CFGImporter(true,true,false);
            importer.importGraph(graph);
            DFGVisitorImport vDFG = importer.getvDFG();

            // import now the graph in GAPP
            GAPPImporter vCFG = new GAPPImporter(importer.getUsedAlgebra(),vDFG.expressions);
            graph.accept(vCFG);

            //outputAssignedArray(vCFG.assigned);


            return graph;


	}

        private void outputAssignedArray(HashMap<GAPPMultivector, BooleanArr> assigned) {
             GAPPMultivector[] arr = assigned.keySet().toArray(new GAPPMultivector[0]);

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
