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
import de.gaalop.gapp.instructionSet.GAPPAssignInputsVector;
import de.gaalop.gapp.variables.GAPPScalarVariable;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.tba.Plugin;
import de.gaalop.tba.cfgImport.CFGImporterFacade;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Facade class to decorate the ControlFlowGraph with GAPP instructions
 * @author Christian Steinmetz
 */
public class GAPPDecoratingMain {
    
    private de.gaalop.gapp.Plugin plugin;

    public GAPPDecoratingMain(de.gaalop.gapp.Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Decorates a given ControlFlowGraph with GAPP instructions
     * @param graph The ControlFlowGraph
     * @return The same graph object (which is now decorated with GAPP instructions)
     * @throws OptimizationException
     */
    public ControlFlowGraph decorateGraph(ControlFlowGraph graph) throws OptimizationException {

        //test if an variable inputsVector exists, if yes, throw an Exception,
        //because inputsVector is used in the following conversion process.
        Variable inputsVectorVar = new Variable("inputsVector");
        if (
                graph.getInputVariables().contains(inputsVectorVar) ||
                graph.getScalarVariables().contains(inputsVectorVar) ||
                graph.getLocalVariables().contains(inputsVectorVar)
            )
                throw new OptimizationException("The usage of 'inputsVector' as a variable is not allowed with using the GAPP optimization!\nPlease rename the 'inputsVector' variable definition and usages!", graph);


        boolean scalarFunctions = plugin.isUseScalarFunctions();

        Plugin plugin = new Plugin();
        
        plugin.setInvertTransformation(true);
        plugin.setScalarFunctions(scalarFunctions);
        plugin.setOptInserting(graph.globalSettings.isOptMaxima());
        plugin.setMaximaExpand(this.plugin.isMaximaExpand());
        
        CFGImporterFacade facade = new CFGImporterFacade(plugin);
        facade.importGraph(graph);

        //Transform divisions with constants in multiplications
        ConstantDivisionTransformer.transform(graph);

        GAPP gappStart = new GAPP();

        HashSet<String> variables = getAllVariableNames(graph);
        assignInputVariables(graph, gappStart);

        // import now the graph in GAPP
        GAPPDecorator vCFG = new GAPPDecorator(gappStart, variables, facade.getUsedAlgebra().getBladeCount(), scalarFunctions, facade.getUsedAlgebra().getAlgebra());
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
     */
    private void assignInputVariables(ControlFlowGraph graph, GAPP gappStart) {
        if (graph.getInputVariables().isEmpty()) return;
        
        LinkedList<Variable> toDo = new LinkedList<Variable>(graph.getInputVariables());
        
        //Sort variable names in for putting in inputsVector for better readability
        Collections.sort(toDo, new Comparator<Variable>() {
            @Override
            public int compare(Variable o1, Variable o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        HashMap<Variable, MultivectorComponent> map = new HashMap<Variable, MultivectorComponent>();

        GAPPVector inputsMv = new GAPPVector("inputsVector");

        Variableset varSet = new Variableset();
        int slotNo = 0;
        for (Variable var : toDo) {
            varSet.add(new GAPPScalarVariable(var.getName()));
            map.put(var, new MultivectorComponent(inputsMv.getName(), slotNo));
            slotNo++;
        }

        gappStart.addInstruction(new GAPPAssignInputsVector(varSet));


        while (!toDo.isEmpty()) {
            Variable curVar = toDo.removeFirst();
            ReplaceVisitor replaceVisitor = new ReplaceVisitor(curVar, map.get(curVar));
            graph.accept(replaceVisitor);
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
