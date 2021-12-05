package de.gaalop.tba.cfgImport;

import de.gaalop.OptimizationException;
import de.gaalop.LoggingListenerGroup;
import de.gaalop.algebra.UpdateLocalVariableSet;
import de.gaalop.api.cfg.RoundingCFGVisitor;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.Variable;
import de.gaalop.tba.baseChange.BaseChanger;
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

    private LinkedList<OptimizationStrategyWithModifyFlag> optimizations;
    private Plugin plugin;
    private UseAlgebra usedAlgebra;
    private LoggingListenerGroup listeners;

    public CFGImporterFacade(Plugin plugin) {
        this.plugin = plugin;
        this.listeners = new LoggingListenerGroup();

        optimizations = new LinkedList<OptimizationStrategyWithModifyFlag>();

        if (plugin.isOptConstantPropagation()) {
            optimizations.add(new OptConstantPropagation());
        }
        if (plugin.isOptUnusedAssignments()) {
            optimizations.add(new OptUnusedAssignmentsRemoval());
        }
        if (plugin.isOptOneExpressionRemoval()) {
            optimizations.add(new OptOneExpressionsRemoval());
        }
    }
    
    public void setProgressListeners(LoggingListenerGroup progressListeners) {
    	listeners = progressListeners;
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

        //load desired algebra
        usedAlgebra = new UseAlgebra(graph.getAlgebraDefinitionFile());

        if (ContainsControlFlow.containsControlFlow(graph)) {
            throw new OptimizationException("Due to Control Flow Existence in Source, TBA isn't assigned on graph!", graph);
        }

        Variable v;
        if ((v = GetMultipleAssignments.getMulipleAssignments(graph)) != null) {
            throw new OptimizationException("Due to Existence of multiple assignments of variable '"+v.getName()+"' in source, TBA isn't assigned on graph!", graph);
        }

        if (!plugin.isInvertTransformation()) {
            DivisionRemover divisionRemover = new DivisionRemover();
            graph.accept(divisionRemover);
        }

        if (!plugin.isScalarFunctions()) {
            VariablesCollector collector = new VariablesCollector();
            graph.accept(collector);

            MathFunctionSeparator mathFunctionSeparator = new MathFunctionSeparator(collector.getVariables());
            graph.accept(mathFunctionSeparator);
        }



        CFGImporter builder = new CFGImporter(usedAlgebra, plugin.isScalarFunctions(), graph.getAlgebraDefinitionFile());
        graph.accept(builder);
        
        if (graph.globalSettings.outputToNormalBase) {
            BaseChanger.changeToNormalBase(graph);
        }

        boolean repeat;
        do {
            repeat = false;
            for (OptimizationStrategyWithModifyFlag curOpt : optimizations) {
                repeat = repeat || curOpt.transform(graph, usedAlgebra, listeners);
            }
        } while (repeat);

        //Use Maxima only once
        if (graph.globalSettings.isOptMaxima()) {
            OptMaxima optMaxima = new OptMaxima(graph.globalSettings.getMaximaCommand(), plugin);
            optMaxima.transform(graph, usedAlgebra, listeners);

            //repeat other optimizations
            do {
                repeat = false;
                for (OptimizationStrategyWithModifyFlag curOpt : optimizations) {
                    repeat = repeat || curOpt.transform(graph, usedAlgebra, listeners);
                }
            } while (repeat);
        }

        // update variable sets
        UpdateLocalVariableSet.updateVariableSets(graph);
        
        // round float constants, if this is desired by the user through configuration panel
        if (plugin.isDoRoundingAfterOptimization()) 
            graph.accept(new RoundingCFGVisitor(plugin.getNumberOfRoundingDigits()));

        return graph;
    }

    public UseAlgebra getUsedAlgebra() {
        return usedAlgebra;
    }

}
