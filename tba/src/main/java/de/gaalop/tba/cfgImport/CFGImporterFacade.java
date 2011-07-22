package de.gaalop.tba.cfgImport;

import de.gaalop.OptimizationException;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.tba.Plugin;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.optimization.OptConstantPropagation;
import de.gaalop.tba.cfgImport.optimization.OptMaxima;
import de.gaalop.tba.cfgImport.optimization.OptOneExpressionsRemoval;
import de.gaalop.tba.cfgImport.optimization.OptimizationStrategyWithModifyFlag;
import de.gaalop.tba.cfgImport.optimization.OptUnusedAssignmentsRemoval;
import java.util.LinkedList;

public class CFGImporterFacade {
    
    private UseAlgebra usedAlgebra;

    private LinkedList<OptimizationStrategyWithModifyFlag> optimizations;
    

    public UseAlgebra getUsedAlgebra() {
        return usedAlgebra;
    }

    public CFGImporterFacade(Plugin plugin) {

        //load 5d conformal algebra
        usedAlgebra = new UseAlgebra();
        usedAlgebra.load5dAlgebra();
        
        optimizations = new LinkedList<OptimizationStrategyWithModifyFlag>();

        if (plugin.isOptConstantPropagation()) optimizations.add(new OptConstantPropagation());
        if (plugin.isOptUnusedAssignments()) optimizations.add(new OptUnusedAssignmentsRemoval());
        if (plugin.isOptOneExpressionRemoval()) optimizations.add(new OptOneExpressionsRemoval());

        if (plugin.isOptMaxima())  optimizations.add(new OptMaxima(plugin.getMaximaCommand()));

    }

    public ControlFlowGraph importGraph(ControlFlowGraph graph) throws OptimizationException {
        
        if (ContainsControlFlow.containsControlFlow(graph)) 
            throw new OptimizationException("Due to Control Flow Existence in Source, TBA isn't assigned on graph!", graph);

        if (ContainsMulipleAssignments.containsMulipleAssignments(graph))
            throw new OptimizationException("Due to Existence of MultipleAssignments in Source, TBA isn't assigned on graph!", graph);

        CFGImporter builder = new CFGImporter(usedAlgebra);
        graph.accept(builder);

        boolean repeat;
        do {
            repeat = false;
            for (OptimizationStrategyWithModifyFlag curOpt: optimizations)
                repeat = repeat || curOpt.transform(graph, usedAlgebra);
        } while (repeat);

        return graph;
    }

}
