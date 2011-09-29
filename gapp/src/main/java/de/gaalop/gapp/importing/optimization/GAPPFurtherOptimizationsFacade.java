package de.gaalop.gapp.importing.optimization;

import de.gaalop.cfg.ControlFlowGraph;

/**
 * This class is a facade class for some further GAPP Optimizations
 * @author Christian Steinmetz
 */
public class GAPPFurtherOptimizationsFacade {

    /**
     * Performs some further GAPP optimizations
     * @param graph
     */
    public void doFurtherGAPPOptimizations(ControlFlowGraph graph) {
        //Remove zero-assignments
        GAPPRemoveZeroAssignments zeroRemover = new GAPPRemoveZeroAssignments();
        graph.accept(zeroRemover);

        //Merge setMv instructions
        GAPPSetMvMerger merger = new GAPPSetMvMerger();
        graph.accept(merger);
        GAPPSetMvRemover remover = new GAPPSetMvRemover(merger.getToRemove());
        graph.accept(remover);
    }

}
