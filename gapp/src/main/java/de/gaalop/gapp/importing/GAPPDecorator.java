package de.gaalop.gapp.importing;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.StartNode;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.importing.parallelObjects.DotProduct;
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPMultivectorComponent;
import java.util.HashSet;

/**
 * Decorates a Control Flow Graph with GAPP instructions
 * @author Christian Steinmetz
 */
public class GAPPDecorator extends EmptyControlFlowVisitor {

    private HashSet<String> createdGAPPVariables = new HashSet<String>();

    private GAPPCreator gappCreator = new GAPPCreator(null);
    private GAPP gappStart;

    public GAPPDecorator(GAPP gappStart) {
        this.gappStart = gappStart;
    }

    private GAPPMultivector createNewMultivector(String name) {
        return new GAPPMultivector(name);
    }

    @Override
    public void visit(AssignmentNode node) {
        // create a new GAPP object and set it as member
        GAPP gapp = new GAPP();
        node.setGAPP(gapp);
        gappCreator.setGapp(gapp);
        if (gappStart != null) {
            gapp.addGAPP(gappStart);
            gappStart = null;
        }

        // remember the name of the current destination multivector component
        MultivectorComponent mvC = (MultivectorComponent) node.getVariable();
        String name = mvC.getName();

        if (!createdGAPPVariables.contains(name)) {
            // create a new GAPPMultivector for destination variable, if it does not exist
            GAPPMultivector mv = createNewMultivector(name);
            gapp.addInstruction(new GAPPResetMv(mv));
            createdGAPPVariables.add(name);
        }

        // process expression
        ExpressionCollector collector = new ExpressionCollector();
        node.getValue().accept(collector);
        ParallelObject parallelObject = collector.getResultValue();


        DotProductsFinder finder = new DotProductsFinder();
        DotProduct newDot = (DotProduct) parallelObject.accept(finder, null);
        if (newDot != null)
            parallelObject = newDot;

        //process further: create GAPP from the ParallelObject data structure
        GAPPMultivectorComponent gMvC = new GAPPMultivectorComponent(mvC.getName(), mvC.getBladeIndex());
        
        parallelObject.accept(gappCreator, gMvC);

        // go further in graph
        super.visit(node);
    }

}
