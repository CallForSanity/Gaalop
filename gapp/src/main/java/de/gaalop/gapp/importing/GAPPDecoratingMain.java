package de.gaalop.gapp.importing;

import de.gaalop.OptimizationException;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.EmptyExpressionVisitor;
import de.gaalop.dfg.ExpressionVisitor;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.importing.optimization.GAPPFurtherOptimizationsFacade;
import de.gaalop.gapp.instructionSet.GAPPAssignVector;
import de.gaalop.gapp.variables.GAPPScalarVariable;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.tba.Plugin;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.BaseVectorChecker;
import de.gaalop.tba.cfgImport.CFGImporterFacade;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Facade class to decorate the ControlFlowGraph with GAPP instructions
 * @author Christian Steinmetz
 */
public class GAPPDecoratingMain {

    /**
     * Decorates a given ControlFlowGraph with GAPP instructions
     * @param graph The ControlFlowGraph
     * @return The same graph object (which is now decorated with GAPP instructions)
     * @throws OptimizationException
     */
    public ControlFlowGraph decorateGraph(UseAlgebra usedAlgebra, ControlFlowGraph graph) throws OptimizationException {

        boolean scalarFunctions = false;

        if (!usedAlgebra.isN3()) {
            BaseVectorChecker checker = new BaseVectorChecker(usedAlgebra.getAlgebra().getBase());
            graph.accept(checker);
        }

        Plugin plugin = new Plugin();
        plugin.setOptInserting(true);
        plugin.setInvertTransformation(true);
        plugin.setScalarFunctions(scalarFunctions);
        plugin.setOptMaxima(true);
        plugin.setMaximaExpand(true);
        CFGImporterFacade facade = new CFGImporterFacade(plugin);
        facade.setUsedAlgebra(usedAlgebra);
        facade.importGraph(graph);

        GAPP gappStart = new GAPP();

        HashSet<String> variables = getAllVariableNames(graph);
        assignInputVariables(graph, gappStart, variables);

        // import now the graph in GAPP
        GAPPDecorator vCFG = new GAPPDecorator(gappStart, variables, usedAlgebra.getBladeCount(), scalarFunctions);
        graph.accept(vCFG);

        // perform further optimizations
        GAPPFurtherOptimizationsFacade furtherOpt = new GAPPFurtherOptimizationsFacade();
        furtherOpt.doFurtherGAPPOptimizations(graph);

        //System.out.println("Memory usage of "+graph.getSource().getName());
        //MemoryUsage.printMemoryUsage(graph);

        return graph;
    }

    /**
     * Adds instructions to a GAPP instance which assigns all InputVariables to a GAPPMultivector
     * @param graph The ControlFlowGraph
     * @param gappStart The GAPP instance
     * @param variables All used variable names in the ControlFlowGraph
     */
    private void assignInputVariables(ControlFlowGraph graph, GAPP gappStart, HashSet<String> variables) {
        LinkedList<Variable> toDo = new LinkedList<Variable>(graph.getInputVariables());

        HashMap<Variable, MultivectorComponent> map = new HashMap<Variable, MultivectorComponent>();

        GAPPVector inputsMv = new GAPPVector(createNameOfInputsVector(variables));

        Variableset varSet = new Variableset();
        int slotNo = 0;
        for (Variable var : toDo) {
            varSet.add(new GAPPScalarVariable(var.getName()));
            map.put(var, new MultivectorComponent(inputsMv.getName(), slotNo));
            slotNo++;
        }

        gappStart.addInstruction(new GAPPAssignVector(inputsMv, varSet));


        while (!toDo.isEmpty()) {
            Variable curVar = toDo.removeFirst();
            ReplaceVisitor replaceVisitor = new ReplaceVisitor(curVar, map.get(curVar));
            graph.accept(replaceVisitor);
        }
    }

    /**
     * Creates a recently non-used name for the inputsVector.
     * Adds the new name to variables set and returns the new name.
     * Preferes "inputsVector" as new name.
     * An increasing number is appended on prefered name until the name is non-used.
     *
     * @param variables
     * @return The new name
     */
    private String createNameOfInputsVector(HashSet<String> variables) {
        // prefer as name "inputsVector"
        String preferedName = "inputsVector";
        if (!variables.contains(preferedName)) {
            variables.add(preferedName);
            return preferedName;
        } else {
            int number = 1;
            while (variables.contains(preferedName + number)) {
                number++;
            }

            String result = preferedName + number;
            variables.add(result);
            return result;
        }
    }

    /**
     * Returns a set of all Variable names in a ControlFlowGraph
     * @param graph The ControlFlowGraph
     * @return The set of all Variable names
     */
    private HashSet<String> getAllVariableNames(ControlFlowGraph graph) {
        final HashSet<String> result = new HashSet<String>();

        ControlFlowVisitor visitor = new EmptyControlFlowVisitor() {

            private ExpressionVisitor visitorExp = new EmptyExpressionVisitor() {

                @Override
                public void visit(MultivectorComponent node) {
                    result.add(node.getName());
                    super.visit(node);
                }

                @Override
                public void visit(Variable node) {
                    result.add(node.getName());
                    super.visit(node);
                }
            };

            @Override
            public void visit(AssignmentNode node) {
                node.getVariable().accept(visitorExp);
                node.getValue().accept(visitorExp);
                super.visit(node);
            }

            @Override
            public void visit(StoreResultNode node) {
                node.getValue().accept(visitorExp);
                super.visit(node);
            }
        };

        graph.accept(visitor);

        return result;
    }
}
