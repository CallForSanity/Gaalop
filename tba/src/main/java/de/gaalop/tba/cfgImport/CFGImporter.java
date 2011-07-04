package de.gaalop.tba.cfgImport;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.optimization.OptConstantPropagation;
import de.gaalop.tba.cfgImport.optimization.OptOneExpressionsRemoval;
import de.gaalop.tba.cfgImport.optimization.OptimizationStrategyWithModifyFlag;
import de.gaalop.tba.cfgImport.optimization.OptUnusedAssignmentsRemoval;
import java.util.LinkedList;

public class CFGImporter {
    //TODO chs: Implement Control Flow!
    private UseAlgebra usedAlgebra;

    private LinkedList<OptimizationStrategyWithModifyFlag> optimizations;

    public CFGImporter() {
        //load 5d conformal algebra
        usedAlgebra = new UseAlgebra();
        usedAlgebra.load5dAlgebra();
        
        optimizations = new LinkedList<OptimizationStrategyWithModifyFlag>();
        
        optimizations.add(new OptUnusedAssignmentsRemoval());
        optimizations.add(new OptConstantPropagation());
        optimizations.add(new OptOneExpressionsRemoval());
    }

    public ControlFlowGraph importGraph(ControlFlowGraph graph) {

        // TODO chs: After implementing Control Flow in TBA CFGImport, remove this statement.
        if (ContainsControlFlow.containsControlFlow(graph)) {
            System.err.println("Due to Control Flow Existence in Source, TBA isn't assigned on graph!");
            return graph;
        }

        DFGVisitorImport vDFG = new DFGVisitorImport(usedAlgebra);
        ControlFlowVisitor vCFG = new CFGVisitorImport(vDFG);
        graph.accept(vCFG);

        boolean repeat;
        do {
            repeat = false;
            for (OptimizationStrategyWithModifyFlag curOpt: optimizations)
                repeat = repeat || curOpt.transform(graph, usedAlgebra);
        } while (repeat);

        return graph;
    }

}
