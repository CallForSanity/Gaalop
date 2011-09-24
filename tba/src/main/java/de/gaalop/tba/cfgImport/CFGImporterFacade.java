package de.gaalop.tba.cfgImport;

import de.gaalop.OptimizationException;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;
import de.gaalop.tba.Plugin;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.optimization.OptConstantPropagation;
import de.gaalop.tba.cfgImport.optimization.OptMaxima;
import de.gaalop.tba.cfgImport.optimization.OptOneExpressionsRemoval;
import de.gaalop.tba.cfgImport.optimization.OptimizationStrategyWithModifyFlag;
import de.gaalop.tba.cfgImport.optimization.OptUnusedAssignmentsRemoval;
import java.util.LinkedList;

/**
 * This class provides a simple facade to transform the graph
 * with the table based approach
 *
 * @author Christian Steinmetz
 */
public class CFGImporterFacade {
    
    private UseAlgebra usedAlgebra;

    private LinkedList<OptimizationStrategyWithModifyFlag> optimizations;

    private Plugin plugin;

    public CFGImporterFacade(Plugin plugin) {
        this.plugin = plugin;

        //load desired algebra
        usedAlgebra = new UseAlgebra(plugin.getAlgebra());
 
        optimizations = new LinkedList<OptimizationStrategyWithModifyFlag>();

        if (plugin.isOptConstantPropagation()) optimizations.add(new OptConstantPropagation());
        if (plugin.isOptUnusedAssignments()) optimizations.add(new OptUnusedAssignmentsRemoval());
        if (plugin.isOptOneExpressionRemoval()) optimizations.add(new OptOneExpressionsRemoval());

        if (plugin.isOptMaxima())  optimizations.add(new OptMaxima(plugin.getMaximaCommand(), plugin));

    }

    /**
     * Transforms the graph and apply optionally optimizations.
     * Note, that the graph is changed in place.
     *
     * @param graph The graph to be transformed
     * @return The transformed graph
     * @throws OptimizationException
     */
    public ControlFlowGraph importGraph(ControlFlowGraph graph) throws OptimizationException {
        
        if (ContainsControlFlow.containsControlFlow(graph)) 
            throw new OptimizationException("Due to Control Flow Existence in Source, TBA isn't assigned on graph!", graph);

        if (ContainsMultipleAssignments.containsMulipleAssignments(graph))
            throw new OptimizationException("Due to Existence of MultipleAssignments in Source, TBA isn't assigned on graph!", graph);

        if (!usedAlgebra.isN3()) {
            BaseVectorChecker checker = new BaseVectorChecker(usedAlgebra.getAlgebra().getBase());
            graph.accept(checker);
        }

        if (!plugin.isScalarFunctions()) {
            
            DivisionRemover divisionRemover = new DivisionRemover();
            graph.accept(divisionRemover);
            
            MathFunctionSeparator mathFunctionSeparator = new MathFunctionSeparator();
            graph.accept(mathFunctionSeparator);
        }

        CFGImporter builder = new CFGImporter(usedAlgebra, plugin.isScalarFunctions());
        graph.accept(builder);

        boolean repeat;
        do {
            repeat = false;
            for (OptimizationStrategyWithModifyFlag curOpt: optimizations)
                repeat = repeat || curOpt.transform(graph, usedAlgebra);
        } while (repeat);

        return graph;
    }

    public void setUsedAlgebra(UseAlgebra usedAlgebra) {
        this.usedAlgebra = usedAlgebra;
    }

    

}
