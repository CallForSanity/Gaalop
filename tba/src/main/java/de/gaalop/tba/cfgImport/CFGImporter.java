package de.gaalop.tba.cfgImport;

import de.gaalop.OptimizationException;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.optimization.OptConstantPropagation;
import de.gaalop.tba.cfgImport.optimization.OptOneExpressionsRemoval;
import de.gaalop.tba.cfgImport.optimization.OptimizationStrategyWithModifyFlag;
import de.gaalop.tba.cfgImport.optimization.OptUnusedAssignmentsRemoval;
import java.util.LinkedList;

public class CFGImporter {

    private UseAlgebra usedAlgebra;

    private LinkedList<OptimizationStrategyWithModifyFlag> optimizations;

    public CFGImporter(boolean gcd) {
        //load 5d conformal algebra
        usedAlgebra = new UseAlgebra();
        usedAlgebra.load5dAlgebra();
        
        optimizations = new LinkedList<OptimizationStrategyWithModifyFlag>();


        optimizations.add(new OptConstantPropagation());

        if (!gcd) {
            optimizations.add(new OptUnusedAssignmentsRemoval());
            optimizations.add(new OptOneExpressionsRemoval());
        }
        
    }

    public ControlFlowGraph importGraph(ControlFlowGraph graph) throws OptimizationException {
        
        if (ContainsControlFlow.containsControlFlow(graph)) 
            throw new OptimizationException("Due to Control Flow Existence in Source, TBA isn't assigned on graph!", graph);

        if (ContainsMulipleAssignments.containsMulipleAssignments(graph))
            throw new OptimizationException("Due to Existence of MultipleAssignments in Source, TBA isn't assigned on graph!", graph);

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
