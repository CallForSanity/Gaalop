package de.gaalop.tba.cfgImport;

import de.gaalop.OptimizationException;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.optimization.OptConstantPropagation;
import de.gaalop.tba.cfgImport.optimization.OptMaxima;
import de.gaalop.tba.cfgImport.optimization.OptOneExpressionsRemoval;
import de.gaalop.tba.cfgImport.optimization.OptimizationStrategyWithModifyFlag;
import de.gaalop.tba.cfgImport.optimization.OptUnusedAssignmentsRemoval;
import java.util.LinkedList;

public class CFGImporter {
    
    private UseAlgebra usedAlgebra;

    private LinkedList<OptimizationStrategyWithModifyFlag> optimizations;

    private DFGVisitorImport vDFG;

    private boolean getOnlyMvExpressions;

    public DFGVisitorImport getvDFG() {
        return vDFG;
    }

    public UseAlgebra getUsedAlgebra() {
        return usedAlgebra;
    }

    

    public CFGImporter(boolean getOnlyMvExpressions, boolean useMaxima) {
        this.getOnlyMvExpressions = getOnlyMvExpressions;

        //load 5d conformal algebra
        usedAlgebra = new UseAlgebra();
        usedAlgebra.load5dAlgebra();
        
        optimizations = new LinkedList<OptimizationStrategyWithModifyFlag>();

        if (!getOnlyMvExpressions) { //TODO chs why to do this when GAPP performing?
            optimizations.add(new OptConstantPropagation());
            optimizations.add(new OptUnusedAssignmentsRemoval());
            optimizations.add(new OptOneExpressionsRemoval());
  
           if (useMaxima) optimizations.add(new OptMaxima());
        }
        
    }

    public ControlFlowGraph importGraph(ControlFlowGraph graph) throws OptimizationException {
        
        if (ContainsControlFlow.containsControlFlow(graph)) 
            throw new OptimizationException("Due to Control Flow Existence in Source, TBA isn't assigned on graph!", graph);

        if (ContainsMulipleAssignments.containsMulipleAssignments(graph))
            throw new OptimizationException("Due to Existence of MultipleAssignments in Source, TBA isn't assigned on graph!", graph);

        vDFG = new DFGVisitorImport(usedAlgebra);
        ControlFlowVisitor vCFG = new CFGVisitorImport(vDFG,getOnlyMvExpressions);
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
