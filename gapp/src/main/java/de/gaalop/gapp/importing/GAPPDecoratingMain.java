package de.gaalop.gapp.importing;

import de.gaalop.OptimizationException;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.Variable;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPScalarVariable;
import de.gaalop.tba.Plugin;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.BaseVectorChecker;
import de.gaalop.tba.cfgImport.CFGImporterFacade;

/**
 * Facade class to decorate the ControlFlowGraph in a GAPP ControlFlowGraph
 * @author Christian Steinmetz
 */
public class GAPPDecoratingMain {

    /**
     * Decorates a given ControlFlowGraph in a GAPP ControlFlowGraph
     * @param graph The ControlFlowGraph
     * @return The same graph object (which is now decorated with GAPP instructions)
     * @throws OptimizationException
     */
    public ControlFlowGraph decorateGraph(UseAlgebra usedAlgebra, ControlFlowGraph graph) throws OptimizationException {

        if (!usedAlgebra.isN3()) {
            BaseVectorChecker checker = new BaseVectorChecker(usedAlgebra.getAlgebra().getBase());
            graph.accept(checker);
        }

        CFGImporterFacade facade = new CFGImporterFacade(new Plugin());
        facade.importGraph(graph);

        GAPP gappStart = new GAPP();
        for (Variable input: graph.getInputVariables()) {
            GAPPMultivector mv = new GAPPMultivector(input.getName(), null);

            gappStart.addInstruction(new GAPPResetMv(mv));

            Selectorset selSet = new Selectorset();
            selSet.add(new Selector(0, (byte) 1));
            Variableset varSet = new Variableset();
            varSet.add(new GAPPScalarVariable(input.getName()));
            gappStart.addInstruction(new GAPPAssignMv(mv, selSet, varSet));
        }

        // import now the graph in GAPP
        GAPPDecorator vCFG = new GAPPDecorator(usedAlgebra.getBladeCount(), gappStart);
        graph.accept(vCFG);

        return graph;
    }

}
